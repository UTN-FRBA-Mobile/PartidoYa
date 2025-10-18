package com.example.partidoya.domain

//NOTE: Se deberia separar jugador de usuario?
data class Jugador(
    val id: Int,
    val nombre: String,
    val apellido: String,
    //val usuario: String,
    val ubicacion: String,
    val modoDeJuego: String,
    //val posicionPreferida: String,
    val presentacion: String
)
