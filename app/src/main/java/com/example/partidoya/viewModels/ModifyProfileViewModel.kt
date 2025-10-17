package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
     description= ""
    )
    )
    val profileData = _profileData.asStateFlow()
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
                    if (result  !=null)
                    Log.i("PROFILE", result.message)
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

        Log.e("INFO","Cambiando datos" + _profileData.value)
    }
}