package com.example.partidoya.domain

import java.time.LocalDate
import java.time.LocalTime

class PartidoEquipo(
    id: Int,
    fecha: LocalDate,
     dia: String,
     horario: LocalTime,
     duracion: Int,
     formato: String,
     cancha: Cancha?,
     barrio: String,
    var hayRepresentante: Boolean
): Partido(id,fecha, dia, horario, duracion, formato, cancha, barrio)
