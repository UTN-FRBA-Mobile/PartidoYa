package com.example.partidoya.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partidoya.Service.RetrofitClient
import com.example.partidoya.domain.Cancha
import com.example.partidoya.domain.Jugador
import com.example.partidoya.domain.Participacion
import com.example.partidoya.domain.PartidoEquipo
import com.example.partidoya.domain.PartidoJugadores
import com.example.partidoya.domain.Partido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class PartidosViewModel() : ViewModel() {


    private val _partidos = MutableStateFlow<List<Partido>>(emptyList())
    val partidos = _partidos.asStateFlow()

    private val _canchas = MutableStateFlow<List<Cancha>>(emptyList())
    val canchas = _canchas.asStateFlow()

    private val _canchasFiltradasPorBarrio = MutableStateFlow<List<Cancha>>(emptyList())
    val canchasFiltradasPorBarrio = _canchasFiltradasPorBarrio.asStateFlow()

    fun cargarCanchas(){
        viewModelScope.launch(Dispatchers.IO) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun nuevoPartido(){ //PARA PROBAR CUANDO NO FUNCIONA LA API
        _partidos.value = _partidos.value + PartidoJugadores(
            LocalDate.of(2025,10,1),
            "Miercoles",
            LocalTime.now(),
            50,
            "Futbol 5",
            Cancha(1,"Fulbo","SS123","Palermo",1.0,0.1, listOf()),
            "Palermo",
            jugadoresFaltantes = 4,
            posicionesFaltantes = listOf("Medio","Delantero"),
            jugadores = emptyList()
        )
    }

    fun canchasPorBarrio(barrioBuscado: String){
        _canchasFiltradasPorBarrio.value = _canchas.value.filter { cancha -> cancha.barrio == barrioBuscado }
    }

    fun crearPartidoJugadores(
        fecha: LocalDate,
        dia: String,
        horario: LocalTime,
        duracion: Int,
        formato: String,
        busqueda: String,
        cancha: Cancha?,
        zona: String,
        cantJugadores: Int,
        posiciones: List<String>)
    {
        val partido = PartidoJugadores(fecha, dia, horario, duracion, formato, cancha, zona, cantJugadores, posiciones, emptyList())

        _partidos.value = _partidos.value + partido
    }

    fun crearPartidoEquipo(
        fecha: LocalDate,
        dia: String,
        horario: LocalTime,
        duracion: Int,
        formato: String,
        cancha: Cancha?,
        zona: String
    )
    {



        val partido = PartidoEquipo(fecha, dia, horario, duracion, formato, cancha, zona)


            _partidos.value = _partidos.value + partido

    }



    fun confirmarPartidoJugadores(partidoConfirmado: PartidoJugadores, posicion: String ? = null){

        //TODO crear una nueva instancia de participacion con el jugador y la posicion elegida
        //TODO hacer un post al backend para modificar el partido

        if(posicion != null) {
            val jugador = Jugador(1, "juan", "perez", "palermo", "competitivo", "capo")



            partidoConfirmado.posicionesFaltantes =
                partidoConfirmado.posicionesFaltantes.filter { pos -> pos != posicion }

            partidoConfirmado.jugadoresFaltantes -= 1


            val participacion = Participacion(partidoConfirmado, jugador, posicion)

             _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
        }
    }

    fun confirmarPartidoEquipo(partidoConfirmado: PartidoEquipo){

        _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
    }


        fun descartarPartido(partidoConfirmado: Partido){
        _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
    }

}