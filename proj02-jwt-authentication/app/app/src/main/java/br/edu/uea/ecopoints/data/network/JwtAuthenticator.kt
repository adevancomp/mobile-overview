package br.edu.uea.ecopoints.data.network

import android.content.SharedPreferences
import android.util.Log
import br.edu.uea.ecopoints.BuildConfig.BASE_URL_API
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import retrofit2.http.HTTP
import java.io.IOException
import javax.inject.Inject

class JwtAuthenticator @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Authenticator {

    private val client = OkHttpClient()

    override fun authenticate(route: Route?, response: Response): Request? {
        //Método executado automaticamente quando a API retornar um erro 401 (Unauthorized)

        //Recuperar o refresh token armazenado no shared preferences
        val refreshToken : String = sharedPreferences.getString("refreshToken", "") ?: ""
        val emailUser : String = sharedPreferences.getString("email","") ?: ""
        val passwordUser: String = sharedPreferences.getString("password", "") ?: ""

        if(refreshToken.isNotBlank() && refreshToken.isNotEmpty()){
            //Com o refresh token irei realizar uma requisição para /aut/refresh
            val jsonRefreshTokenRequest = JSONObject().apply {
                put("token",refreshToken)
            }
            val jsonNewLoginRequest = JSONObject().apply {
                put("email",emailUser)
                put("password",passwordUser)
            }
            val refreshTokenRequest : Request = Request.Builder()
                .url("${BASE_URL_API}/auth/refresh")
                .post(
                    jsonRefreshTokenRequest
                        .toString()
                        .toRequestBody(
                            "application/json; charset=utf-8".toMediaType()
                        )
                ).build()
            val newLoginRequest : Request = Request.Builder()
                .url("${BASE_URL_API}/auth")
                .post(
                    jsonNewLoginRequest
                        .toString()
                        .toRequestBody(
                            "application/json; charset=utf-8".toMediaType()
                        )
                ).build()

            try {
                val refreshTokenResponse = client.newCall(refreshTokenRequest).execute()
                if(refreshTokenResponse.isSuccessful){
                    var newAccessToken: String = refreshTokenResponse.body?.string() ?: ""
                    val newAccessTokenObject: JSONObject = JSONObject(newAccessToken)
                    newAccessToken = newAccessTokenObject.getString("token")
                    Log.i("ECO","JwtAuthenticator: AccessToken $newAccessToken")
                    with(sharedPreferences.edit()){
                        putString("accessToken",newAccessToken)
                        commit()
                    }
                    return response.request
                        .newBuilder()
                        .header("Authorization","Bearer $newAccessToken").build()
                } else if(refreshTokenResponse.code==401){
                    //Provável que eu precise renovar o refreshToken, porque ele expirou
                    Log.i("ECO","JwtAuthenticator: 401 na resposta do /auth/refresh")
                    val newLoginResponse = client.newCall(newLoginRequest).execute()
                    if(newLoginResponse.isSuccessful){
                        Log.i("ECO","JwtAuthenticator: Requisição em /auth bem sucedida")
                        val newLoginTokens = newLoginResponse.body?.string() ?: ""
                        val newLoginTokensObject = JSONObject(newLoginTokens)
                        val newLoginAccessToken = newLoginTokensObject.getString("accessToken")
                        val newLoginRefreshToken = newLoginTokensObject.getString("refreshToken")
                        with(sharedPreferences.edit()){
                            putString("accessToken",newLoginAccessToken)
                            putString("refreshToken",newLoginRefreshToken)
                            commit()
                        }
                        Log.i("ECO","Todas as operações e salvou o novo token")
                        return response.request
                            .newBuilder()
                            .header("Authorization","Bearer $newLoginAccessToken").build()
                    }else{
                        Log.i("ECO","JwtAuthenticator: Tomei erro no /auth pra fazer novo login ${newLoginResponse.code}")
                    }
                } else {
                    Log.e("ECO","JwtAuthenticator: Erro ao executar o refresh token em /aut/refresh ${refreshTokenResponse.code}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}