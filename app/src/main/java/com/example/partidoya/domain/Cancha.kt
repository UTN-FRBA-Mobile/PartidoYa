package com.example.partidoya.domain

data class Cancha(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val barrio: String,
    val lat: Double,
    val lon: Double,
    val tiposDeCanchas: List<Int>
)
