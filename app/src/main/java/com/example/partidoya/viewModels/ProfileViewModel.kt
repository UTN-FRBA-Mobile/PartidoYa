package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.RetrofitClient
import com.example.partidoya.domain.Jugador
import com.example.partidoya.dto.res.UserProfileRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel(){

    private val _profileData = MutableStateFlow<UserProfileRes?>(null)
    val profileData = _profileData.asStateFlow()

    var logoutData by mutableStateOf(LogoutUiState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerDatosDelPerfil(){
        viewModelScope.launch(Dispatchers.IO){
            try {

                val response = RetrofitClient.userService.getProfileData()
                if (response.isSuccessful)
                    _profileData.value = response.body()
            }
            catch (e: Exception){
                Log.e("API PERFIL", e.message, e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun logout(){
        viewModelScope.launch(Dispatchers.IO){
            logoutData = logoutData.copy(isLoading = true)
            try {
                RetrofitClient.userService.logoutUser()
            }
            catch (e: Exception){
                Log.e("API PERFIL", e.message, e)
            } finally {
                logoutData = logoutData.copy(isLoading = false, logoutSuccess = true)
            }
        }
    }
}

data class LogoutUiState(
    val isLoading: Boolean = false,
    val logoutSuccess: Boolean = false
)