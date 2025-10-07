package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

data class PartidoEquipo(
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha?,
    val barrio: String,
    val representante: Jugador? = null
):
    Partido(fecha, dia, horario, duracion, formato, cancha, barrio)
