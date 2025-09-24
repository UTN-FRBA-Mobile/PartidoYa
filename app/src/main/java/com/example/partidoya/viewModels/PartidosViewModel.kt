package com.example.partidoya.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.RetrofitClient
import com.example.partidoya.domain.Cancha
import com.example.partidoya.domain.Partido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class PartidosViewModel() : ViewModel() {
    private val _partidosIncompletos: MutableLiveData<List<Partido>> = MutableLiveData(emptyList())
    val partidosIncompletos: LiveData<List<Partido>> = _partidosIncompletos

    private val _canchas: MutableLiveData<List<Cancha>> = MutableLiveData(emptyList())
    val canchas: LiveData<List<Cancha>> = _canchas

    private val _canchasFiltradasPorBarrio: MutableLiveData<List<Cancha>> = MutableLiveData(emptyList())
    val canchasFiltradasPorBarrio: LiveData<List<Cancha>> = _canchasFiltradasPorBarrio


    fun cargarCanchas(){
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.footballFieldsService.list()
                if(response.isSuccessful)
                    _canchas.value = response.body() ?: emptyList()
            }
            catch (e: Exception){
                Log.e("API CANCHAS", e.message, e)
            }
        }

    }

    fun canchasPorBarrio(barrioBuscado: String){
        _canchasFiltradasPorBarrio.value = _canchas.value.filter { cancha -> cancha.barrio == barrioBuscado }
    }

    fun crearPartido(
        fecha: LocalDate,
        dia: String,
        horario: LocalTime,
        duracion: Int,
        formato: String,
        busqueda: String,
        cancha: Cancha?,
        zona: String,
        cantJugadores: Int? = null,
        posiciones: List<String>? = null)
    {

        val partido: Partido

        if(cancha != null) {
            if (busqueda == "JUGADORES")
                partido = Partido(fecha, dia, horario, duracion, formato, cancha, zona, busqueda, cantJugadores, posiciones
                )
            else
                partido = Partido(fecha, dia, horario, duracion, formato, cancha, zona, busqueda)


            _partidosIncompletos.value = _partidosIncompletos.value + partido
        }



    }

}