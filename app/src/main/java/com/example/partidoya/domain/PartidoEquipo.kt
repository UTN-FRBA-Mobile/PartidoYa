package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

class PartidoEquipo(
     fecha: LocalDate,
     dia: String,
     horario: LocalTime,
     duracion: Int,
     formato: String,
     cancha: Cancha?,
     barrio: String,
    val representante: Jugador? = null
):
    Partido(fecha, dia, horario, duracion, formato, cancha, barrio)
