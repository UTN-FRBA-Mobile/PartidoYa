package com.example.partidoya.domain

data class Jugador(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val ubicacion: String,
    val modoDeJuego: String,
    val presentacion: String
)
