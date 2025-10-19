package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
}