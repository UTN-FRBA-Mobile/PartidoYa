package com.example.partidoya.dto.req

import com.example.partidoya.domain.Cancha
import java.time.LocalDate
import java.time.LocalTime

class PartidoJugReq(
    tipo: String,
    fecha: LocalDate,
    dia: String,
    horario: LocalTime,
    duracion: Int,
    formato: String,
    cancha: Cancha?,
    barrio: String,
    var jugadoresFaltantes: Int,
    var posicionesFaltantes: List<String>,
    //var jugadores: List<Participacion>
):  PartidoReq(tipo,fecha, dia, horario, duracion, formato, cancha, barrio)
