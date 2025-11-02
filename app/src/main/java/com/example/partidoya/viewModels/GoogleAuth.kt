package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.GoogleLoginRequest
import com.example.partidoya.Service.LoginRequest
import com.example.partidoya.Service.RetrofitClient
import kotlinx.coroutines.launch

class GoogleAuth: ViewModel(){


    var uiState by mutableStateOf(GoogleAuthUiState())
        private set
    @RequiresApi(Build.VERSION_CODES.O)
    fun auth(token: String){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val response = RetrofitClient.authService
                    .googleLogin(GoogleLoginRequest(token))
                uiState = uiState.copy(isLoading = false, loginSuccess = true, token = response.token)
            } catch (e: Exception) {
                Log.e("AUTH LOGIN", e.message.toString())
                uiState = uiState.copy(isLoading = false, error = "Error al intentar loguearse")
            }
        }

    }
}

data class GoogleAuthUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val token: String? = null,
    val error: String? = null
)