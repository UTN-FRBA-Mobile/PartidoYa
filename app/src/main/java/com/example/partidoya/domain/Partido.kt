package com.example.partidoya.domain

data class Partido(
    val id: Int,
    val formato: String,
    val tipo: String,
    val dia: String,
    val horario: String,
    val zona: String,
    val cancha: String,
    val distancia: Int,
    val jugadoresFaltantes: Int,
    val posiciones: String
)
