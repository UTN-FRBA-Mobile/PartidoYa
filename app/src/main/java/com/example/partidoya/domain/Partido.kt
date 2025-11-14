package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

abstract class Partido(
    val id: Int,
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha?,
    val barrio: String,
    val puedeCancelar: Boolean,
    val detalleJugadores: List<DetalleJugador>? = null
)