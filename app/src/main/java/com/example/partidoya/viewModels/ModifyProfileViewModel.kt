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
import com.example.partidoya.domain.ApiResponse
import com.example.partidoya.domain.Jugador
import com.example.partidoya.dto.req.UserProfileReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty1


class ModifyProfileViewModel:ViewModel() {
    private val _profileData = MutableStateFlow<UserProfileReq?>(UserProfileReq(
     name= "",
     surname  = "",
    userIdentifier = "",
     preferedPosition = "",
     playStyle= "",
    location  = "",
     description= "",
    celular="")
    )

    private val _barrios = MutableStateFlow<List<String>>(emptyList())
    val barrios = _barrios.asStateFlow()

    val profileData = _profileData.asStateFlow()
    var uiState by mutableStateOf(modifyProfileUiState())
        private set
    @RequiresApi(Build.VERSION_CODES.O)
    fun modificarPerfil(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val body = _profileData.value
                if (body==null){
                   Log.e("API PERFIL", "Profile data is null")
                    return@launch
                }
                val response = RetrofitClient.userService.updateProfileData(body)
                if (response.isSuccessful) {
                    val result: ApiResponse? = response.body()
                    if (result  !=null) {
                        Log.i("PROFILE", result.message)
                        uiState = uiState.copy(success = true)
                    }
                    else{
                        Log.e("PROFILE", "Response body was null")
                    }}
            }
            catch (e: Exception){
                Log.e("API PERFIL", e.message, e)
            }
        }

    }
    fun <T> onProfileChanged(transform: UserProfileReq.()-> UserProfileReq) {
        _profileData.value = _profileData.value?.transform()

        Log.i("INFO","Cambiando datos" + _profileData.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarBarrios(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.getBarrios()
                if(response.isSuccessful) {
                    _barrios.value = response.body() ?: emptyList()
                }
            }
            catch (e: Exception){
                Log.e("API BARRIOS", e.message, e)
            }
        }

    }
}

data class modifyProfileUiState(
    val success: Boolean = false
)

