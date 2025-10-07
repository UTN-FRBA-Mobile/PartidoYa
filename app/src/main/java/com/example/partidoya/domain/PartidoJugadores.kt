package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

data class PartidoJugadores(
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha?,
    val barrio: String,
    var jugadoresFaltantes: Int,
    var posicionesFaltantes: List<String>,
    var jugadores: List<Participacion>
):
    Partido(fecha, dia, horario, duracion, formato, cancha, barrio)

