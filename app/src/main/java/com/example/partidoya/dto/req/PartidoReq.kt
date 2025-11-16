package com.example.partidoya.dto.req

import com.example.partidoya.domain.Cancha
import java.time.LocalDate
import java.time.LocalTime

class PartidoReq (
    val tipo: String,
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha?,
    val barrio: String,
    val reputacionMinima: Int,
    var jugadoresFaltantes: Int,
    var posicionesFaltantes: List<String>,
    var seriedad: String
    )