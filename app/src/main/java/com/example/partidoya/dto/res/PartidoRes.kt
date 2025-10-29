package com.example.partidoya.dto.res

import com.example.partidoya.domain.Cancha
import java.time.LocalDate
import java.time.LocalTime

class PartidoRes(
    val id: Int,
    val tipo: String,
    val fecha: LocalDate,
    val dia: String,
    val horario: LocalTime,
    val duracion: Int,
    val formato: String,
    val cancha: Cancha?,
    val barrio: String,
    val hayRepresentante: Boolean,
    var jugadoresFaltantes: Int,
    var posicionesFaltantes: List<String>
)