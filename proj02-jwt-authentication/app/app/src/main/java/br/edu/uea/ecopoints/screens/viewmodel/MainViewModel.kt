package br.edu.uea.ecopoints.screens.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.uea.ecopoints.data.network.api.EcoApi
import br.edu.uea.ecopoints.data.network.requests.AuthenticationRequest
import br.edu.uea.ecopoints.data.network.responses.AuthenticationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: EcoApi,
    private val shared: SharedPreferences
) : ViewModel() {
    private val _state = MutableLiveData<LoginState>()
    val state : LiveData<LoginState> = _state

    fun authenticate(email: String, password: String){
        _state.value = LoginState.Loading
        viewModelScope.launch {
            _state.value = runCatching {
                api.login(AuthenticationRequest(email,password))
            }.fold(
                onSuccess = {
                    response ->
                        when{
                            response.isSuccessful -> {
                                with(shared.edit()){
                                    putString("role",response.body()?.role)
                                    putString("accessToken", response.body()?.accessToken)
                                    putString("refreshToken", response.body()?.refreshToken)
                                    putString("email",email)
                                    putString("password",password)
                                    commit()
                                }
                                LoginState.Success(response.body() ?: AuthenticationResponse("","",""))
                            }
                            response.code() == 403 -> {
                                LoginState.Error("Usuário ou senha inválida")
                            }
                            else ->
                                LoginState.Error("Erro na API ${response.code()}")
                        }
                }, onFailure = {
                    error ->
                        LoginState.Error(error.message ?: "Erro não identificado")
                }
            )
        }
    }
}

sealed interface LoginState {
    val auth: AuthenticationResponse?
        get() = null
    val isProgressVisible: Boolean
        get() = false
    val errorMessage: String?
        get() = null

    val isErrorMessageVisible: Boolean
        get() = errorMessage != null

    data class Success(override val auth: AuthenticationResponse) : LoginState

    data object Loading : LoginState {
        override val isProgressVisible: Boolean = true
    }

    data class Error(override val errorMessage: String) : LoginState
}