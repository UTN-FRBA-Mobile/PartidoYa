package com.example.partidoya.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.partidoya.domain.Partido
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PartidosViewModel() : ViewModel() {
    private val _partidosIncompletos: MutableLiveData<List<Partido>> = MutableLiveData(emptyList())
    val partidosIncompletos: LiveData<List<Partido>> = _partidosIncompletos


    fun cargarPartidos() { //Aca deberia consultarse el almacenamiento
       /* val p1 = Partido(1,"FUTBOL 5","Entre amigos","Lunes","18:00","Caballito","ATENEO FUTBOL", 5,2,"Arquero, Defensor")
        val p2 = Partido(2,"FUTBOL 11","Torneo","Martes","19:00","Quilmes","ATENEO FUTBOL", 10,2,"-")
        val p3 = Partido(3,"FUTBOL 7","Entre amigos","Jueves","22:00","Recoleta","ATENEO FUTBOL", 4,1,"Defensor")
        val p4 = Partido(1,"FUTBOL 5","Serio","Lunes","18:00","Caballito","ATENEO FUTBOL", 5,2,"Arquero, Defensor")
        val p5 = Partido(2,"FUTBOL 11","Torneo","Martes","19:00","Quilmes","ATENEO FUTBOL", 10,2,"-")
        val p6 = Partido(3,"FUTBOL 7","Entre amigos","Jueves","22:00","Recoleta","ATENEO FUTBOL", 4,1,"Defensor")

        _partidosIncompletos.value = listOf(p1,p2,p3,p4,p5,p6)*/
    }

    fun crearPartido(fecha: LocalDate,
                     dia: String,
                     horario: LocalTime,
                     duracion: Int,
                     formato: String,
                     busqueda: String,
                     cancha: String,
                     zona: String,
                     cantJugadores: Int? = null,
                     posiciones: List<String>? = null)
    {

        val partido: Partido

        if(busqueda == "JUGADORES")
            partido = Partido(fecha,dia,horario,duracion,formato,cancha,zona,busqueda,cantJugadores,posiciones)
        else
            partido =  Partido(fecha,dia,horario,duracion,formato,cancha,zona,busqueda)


        _partidosIncompletos.value = _partidosIncompletos.value + partido

    }

}