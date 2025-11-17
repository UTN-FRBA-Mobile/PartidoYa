package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.RetrofitClient
import com.example.partidoya.domain.ApiResponse
import com.example.partidoya.domain.Barrio
import com.example.partidoya.domain.Jugador
import com.example.partidoya.domain.Option
import com.example.partidoya.dto.req.UserProfileReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.reflect.KMutableProperty1
import kotlinx.coroutines.flow.map



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
    private val _playStyles = MutableStateFlow<List<Option>>(emptyList())
    val playStyles = _playStyles.asStateFlow()
    private val _positions = MutableStateFlow<List<Option>>(emptyList())
    val positions = _positions.asStateFlow()

/* Cambios de ciro que entran en conflicto
    val barrioOptions = _barrios.map { barrios ->
        barrios.map { barrio ->
            Option(barrio.id.toString(), barrio.nombre)
        }
    }
 */

    var uiState by mutableStateOf(modifyProfileUiState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarPlayStyles(){
        getRequest(
            {RetrofitClient.optionsService.getPlayStyleOptions()},
            _playStyles,
            "PLAYSTYLES"
        )


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarPosiciones(){
        getRequest(
            {RetrofitClient.optionsService.getPositionOptions()},
            _positions,
            "POSITION"
        )
    }

/* Cambios de ciro que entran en conflicto
    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarBarrios(){
        getRequest(
            {RetrofitClient.barrioService.getBarrios()},
            _barrios,
            "BARRIOS"
        )
    }

 */

    @RequiresApi(Build.VERSION_CODES.O)
    private fun <T>getRequest(
        requestCall: suspend () -> Response<T>,
        targetLiveData: MutableStateFlow<T>,
        logTag: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = requestCall()

                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        Log.i(logTag, result.toString())
                        targetLiveData.value = result;
                    } else {
                        Log.e(logTag, "Response body was null")
                    }
                }
                else {
                    Log.e(logTag, "Unsuccessful response: ${response.code()}")
                }
            }
            catch (e: Exception){
                Log.e("API OPTIONS", e.message, e)
            }
        }

    }


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
                    }
                }
                else {
                    Log.e("PROFILE", "Unsuccessful response: ${response.code()}, ${response.body()}")
                }
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
                    val barrios: List<Barrio> = response.body() ?: emptyList()
                    _barrios.value = barrios.map { it.nombre }
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

