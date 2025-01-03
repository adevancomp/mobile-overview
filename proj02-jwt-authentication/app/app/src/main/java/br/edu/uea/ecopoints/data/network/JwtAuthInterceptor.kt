package br.edu.uea.ecopoints.data.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class JwtAuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
)  : Interceptor {

    private var accessToken = sharedPreferences.getString("accessToken","") ?: ""
    private var credentials = "Bearer $accessToken"

    override fun intercept(chain: Interceptor.Chain): Response {
        //Recupera os dados mais recentes
        accessToken = sharedPreferences.getString("accessToken","") ?: ""
        credentials = "Bearer $accessToken"

        if(accessToken.isBlank() || accessToken.isEmpty()){
            return chain.proceed(chain.request())
        }

        val original : Request = chain.request()

        val requestBuilder : Request.Builder = original
            .newBuilder()
            .header("Authorization", credentials)

        val request : Request = requestBuilder.build()

        return chain.proceed(request)
    }
}