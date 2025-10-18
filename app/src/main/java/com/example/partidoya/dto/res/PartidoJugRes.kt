package com.example.partidoya.dto.res

import com.example.partidoya.domain.Cancha
import java.time.LocalDate
import java.time.LocalTime

class PartidoJugRes(
    id: Int,
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
):  PartidoRes(id,tipo,fecha, dia, horario, duracion, formato, cancha, barrio)