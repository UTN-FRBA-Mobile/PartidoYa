package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.LoginRequest
import com.example.partidoya.Service.RetrofitClient
import kotlinx.coroutines.launch

class NewAccountViewModel() : ViewModel() {
    var uiState by mutableStateOf(NewAccountUiState())
        private set

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onPasswordRepeatableChange(password: String) {
        uiState = uiState.copy(passwordRepeatable = password)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun register() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val response = RetrofitClient.authService.registerUser(LoginRequest(uiState.email, uiState.password))
                uiState = uiState.copy(isLoading = false, registerSuccess = true, token = response.token)
            } catch (e: Exception) {
                Log.e("AUTH REGISTER", e.message.toString())
                uiState = uiState.copy(isLoading = false, error = "Error al intentar registrarse")
            }
        }
    }
}

data class NewAccountUiState(
    val email: String = "",
    val password: String = "",
    val passwordRepeatable: String = "",
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val token: String? = null,
    val error: String? = null
)