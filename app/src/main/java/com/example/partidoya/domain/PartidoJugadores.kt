package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

class PartidoJugadores(
     fecha: LocalDate,
     dia: String,
     horario: LocalTime,
     duracion: Int,
     formato: String,
     cancha: Cancha?,
     barrio: String,
    var jugadoresFaltantes: Int,
    var posicionesFaltantes: List<String>,
    //var jugadores: List<Participacion>
):
    Partido(fecha, dia, horario, duracion, formato, cancha, barrio)

