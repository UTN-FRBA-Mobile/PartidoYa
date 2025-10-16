package com.example.partidoya.dto.res

import com.example.partidoya.domain.Cancha
import com.example.partidoya.domain.Jugador
import com.example.partidoya.dto.req.PartidoReq
import java.time.LocalDate
import java.time.LocalTime

class PartidoEquiRes(
    id: Int,
    tipo: String,
    fecha: LocalDate,
    dia: String,
    horario: LocalTime,
    duracion: Int,
    formato: String,
    cancha: Cancha?,
    barrio: String,
    val representante: Jugador
): PartidoRes(id,tipo,fecha, dia, horario, duracion, formato, cancha, barrio)