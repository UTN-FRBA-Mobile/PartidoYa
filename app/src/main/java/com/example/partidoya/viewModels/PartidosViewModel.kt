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
import com.example.partidoya.dto.req.ParticipacionReq
import com.example.partidoya.dto.req.PartidoEquiReq
import com.example.partidoya.dto.req.PartidoJugReq
import com.example.partidoya.dto.req.PartidoReq
import com.example.partidoya.dto.res.ParticipacionRes
import com.example.partidoya.dto.res.PartidoEquiRes
import com.example.partidoya.dto.res.PartidoJugRes
import com.example.partidoya.main.ToastMatchCreated
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class PartidosViewModel() : ViewModel() {


    private val _partidos = MutableStateFlow<List<Partido>>(emptyList())
    val partidos = _partidos.asStateFlow()

    private val _participaciones = MutableStateFlow<List<ParticipacionRes>>(emptyList())
    val participaciones = _participaciones.asStateFlow()

    private val _misPartidos = MutableStateFlow<List<Partido>>(emptyList())
    val misPartidos = _misPartidos.asStateFlow()

    private val _canchas = MutableStateFlow<List<Cancha>>(emptyList())
    val canchas = _canchas.asStateFlow()

    private val _canchasFiltradasPorBarrio = MutableStateFlow<List<Cancha>>(emptyList())
    val canchasFiltradasPorBarrio = _canchasFiltradasPorBarrio.asStateFlow()

    private val _partidoConfirmado = MutableStateFlow<Partido?>(null)
    val partidoConfirmado = _partidoConfirmado.asStateFlow()

    private val _barrios = MutableStateFlow<List<String>>(emptyList())
    val barrios = _barrios.asStateFlow()

    private val _filtroJugEqui = MutableStateFlow<String>("Jugadores")
    val filtroJugEqui = _filtroJugEqui.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarCanchas(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.list()
                if(response.isSuccessful) {
                    _canchas.value = response.body() ?: emptyList()
                    _barrios.value = barriosConCanchas()
                }
            }
            catch (e: Exception){
                Log.e("API CANCHAS", e.message, e)
            }
        }

    }

    fun canchasPorBarrio(barrioBuscado: String){
        _canchasFiltradasPorBarrio.value = _canchas.value.filter { cancha -> cancha.barrio == barrioBuscado }
    }

    fun barriosConCanchas(): List<String>{
        val barriosDisp = _canchas.value.map { cancha -> cancha.barrio }
        return barriosDisp.distinct()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarPartidosJugadores(){

        //CARGAR PARTIDOS DE JUGADORES

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.getMatchesJug()
                if(response.isSuccessful) {
                    val partidosRes = response.body() ?: emptyList()

                    _partidos.value = partidosRes.map { p ->
                        PartidoJugadores(
                            id = p.id,
                            fecha = p.fecha,
                            dia = p.dia,
                            horario = p.horario,
                            duracion = p.duracion,
                            formato = p.formato,
                            cancha = p.cancha,
                            barrio = p.barrio,
                            jugadoresFaltantes = p.jugadoresFaltantes,
                            posicionesFaltantes = p.posicionesFaltantes
                        )
                    }
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cargarPartidosEquipo(){

        //CARGAR PARTIDOS DE EQUIPOS

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.getMatchesEqui()
                if(response.isSuccessful) {
                    val partidosRes = response.body() ?: emptyList()

                    _partidos.value = partidosRes.map { p ->
                        PartidoEquipo(
                            id = p.id,
                            fecha = p.fecha,
                            dia = p.dia,
                            horario = p.horario,
                            duracion = p.duracion,
                            formato = p.formato,
                            cancha = p.cancha,
                            barrio = p.barrio,
                        )
                    }
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        val partidoJugReq = PartidoJugReq(
            tipo = "jugadores",
            fecha = fecha,
            dia = dia,
            horario =  horario,
            duracion = duracion,
            formato = formato,
            cancha = cancha,
            barrio = zona,
            jugadoresFaltantes = cantJugadores,
            posicionesFaltantes = posiciones
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.newMatchJug(partidoJugReq)
                if(response.isSuccessful)
                    Log.d("API PARTIDOS", "CREADO CON EXITO")
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        val partidoEqReq = PartidoEquiReq(
            tipo = "equipo",
            fecha = fecha,
            dia = dia,
            horario =  horario,
            duracion = duracion,
            formato = formato,
            cancha = cancha,
            barrio = zona
        )


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.newMatchEqui(partidoEqReq)
                if(response.isSuccessful)
                    Log.d("API PARTIDOS", "CREADO CON EXITO")
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmarPartidoJugadores(partidoConfirmado: PartidoJugadores, posicion: String){

        val participacionReq = ParticipacionReq(posicion)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.joinMatchJug(partidoConfirmado.id, participacionReq)
                if(response.isSuccessful) {
                    _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
                    Log.d("API PARTIDOS", "NUEVA PARTICIPACION EXITOSA")
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }


    fun confirmarPartidoEquipo(partidoConfirmado: PartidoEquipo){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.joinMatchEqui(partidoConfirmado.id)
                if(response.isSuccessful) {
                    _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
                    Log.d("API PARTIDOS", "NUEVO EQUIPO EXITOSO")
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }

    fun cargarMisPartidosJugadores(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.getMyParticipations()
                if(response.isSuccessful) {
                    _participaciones.value = response.body() ?: emptyList()
                    Log.d("API PARTIDOS", "PARTICIPACIONES OBTENIDAS EXITOSAMENTE")
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }

                try {
                    val response = RetrofitClient.footballFieldsService.getMyMatchesJug()
                    if (response.isSuccessful) {
                        val partidosRes = response.body() ?: emptyList()

                        _misPartidos.value = partidosRes.map { p ->
                            PartidoJugadores(
                                id = p.id,
                                fecha = p.fecha,
                                dia = p.dia,
                                horario = p.horario,
                                duracion = p.duracion,
                                formato = p.formato,
                                cancha = p.cancha,
                                barrio = p.barrio,
                                jugadoresFaltantes = p.jugadoresFaltantes,
                                posicionesFaltantes = p.posicionesFaltantes
                            )
                        }
                        Log.d("API PARTIDOS", "MIS PARTIDOS JUGADORES CARGADOS EXITOSAMENTE")
                    }
                } catch (e: Exception) {
                    Log.e("API PARTIDOS", e.message, e)
                }
        }

    }

    fun cargarMisPartidosEquipo(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.getMyMatchesEqui()
                if(response.isSuccessful) {
                    val partidosRes = response.body() ?: emptyList()

                    _misPartidos.value = partidosRes.map { p ->
                        PartidoEquipo(
                            id = p.id,
                            fecha = p.fecha,
                            dia = p.dia,
                            horario = p.horario,
                            duracion = p.duracion,
                            formato = p.formato,
                            cancha = p.cancha,
                            barrio = p.barrio
                        )
                    }
                    Log.d("API PARTIDOS", "MIS PARTIDOS EQUIPO CARGADOS EXITOSAMENTE")
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }

    fun descartarPartido(partidoConfirmado: Partido){
        _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
    }

    fun eliminarPartidoConfirmado(){
        _partidoConfirmado.value = null
    }

    fun alternarFiltroJugEqui(){
        _filtroJugEqui.value =  if(_filtroJugEqui.value == "Jugadores") "Equipo" else  "Jugadores"
    }

    fun abandonarPartido(partido: Partido){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.footballFieldsService.joinMatchEqui(partido.id)
                if(response.isSuccessful) {
                    _partidos.value = _partidos.value.filter { partido -> partido != partidoConfirmado }
                    Log.d("API PARTIDOS", "NUEVO EQUIPO EXITOSO")
                }
            }
            catch (e: Exception){
                Log.e("API PARTIDOS", e.message, e)
            }
        }
    }

    fun posicionElegida(partido: Partido): String{
       return _participaciones.value.filter { participacion -> participacion.partidoId.equals(partido.id) }.get(0).posicion
    }

}