package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

data class Partido(
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha,
    val barrio: String,
    //val distancia: Int,
    val busqueda: String,
    val jugadoresFaltantes: Int ? = null,
    val posiciones: List<String> ? = null
)
