package com.example.partidoya.dto.req

import com.example.partidoya.domain.Cancha
import com.example.partidoya.domain.Jugador
import com.example.partidoya.domain.Partido
import java.time.LocalDate
import java.time.LocalTime

class PartidoEquiReq(
    tipo: String,
    fecha: LocalDate,
    dia: String,
    horario: LocalTime,
    duracion: Int,
    formato: String,
    cancha: Cancha?,
    barrio: String,
    //val representante: Jugador
): PartidoReq(tipo,fecha, dia, horario, duracion, formato, cancha, barrio)