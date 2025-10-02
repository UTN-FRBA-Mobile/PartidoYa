package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

abstract class Partido(
    fecha: LocalDate,
    dia: String,
    horario: LocalTime,
    duracion: Int,
    formato: String,
    cancha: Cancha?,
    barrio: String
){

}