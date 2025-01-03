package br.edu.uea.ecopoints.di

import android.content.SharedPreferences
import br.edu.uea.ecopoints.BuildConfig.BASE_URL_API
import br.edu.uea.ecopoints.data.network.JwtAuthInterceptor
import br.edu.uea.ecopoints.data.network.JwtAuthenticator
import br.edu.uea.ecopoints.data.network.api.EcoApi
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesJwtAuthInterceptor(sharedPreferences: SharedPreferences) : JwtAuthInterceptor {
        return JwtAuthInterceptor(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesJwtAuthentication(sharedPreferences: SharedPreferences) : JwtAuthenticator {
        return JwtAuthenticator(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(jwtAuthInterceptor: JwtAuthInterceptor, jwtAuthenticator: JwtAuthenticator) : OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager{
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {}
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        val sslSocketFactory = sslContext.socketFactory
        return OkHttpClient
            .Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .addInterceptor(jwtAuthInterceptor)
            .authenticator(jwtAuthenticator)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    ObjectMapper().apply {
                        registerModule(JavaTimeModule())
                        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    }
                )
            ).client(client).build()
    }

    @Singleton
    @Provides
    fun providesEcoApiService(retrofit: Retrofit) : EcoApi {
        return retrofit.create(EcoApi::class.java)
    }
}