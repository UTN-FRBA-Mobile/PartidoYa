package com.example.partidoya.Service

import com.example.partidoya.domain.Cancha
import com.example.partidoya.dto.req.ParticipacionReq
import com.example.partidoya.dto.req.PartidoEquiReq
import com.example.partidoya.dto.req.PartidoJugReq
import com.example.partidoya.dto.req.PartidoReq
import com.example.partidoya.dto.res.ParticipacionRes
import com.example.partidoya.dto.res.PartidoEquiRes
import com.example.partidoya.dto.res.PartidoJugRes
import com.example.partidoya.dto.res.PartidoRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FootballFieldsService {
    @GET("/api/canchas")
    suspend fun list(): Response<List<Cancha>>


    @POST("/api/partidos")
    suspend fun newMatchJug(@Body partido: PartidoJugReq): Response<PartidoJugRes>
    @POST("/api/partidos")
    suspend fun newMatchEqui(@Body partido: PartidoEquiReq): Response<PartidoEquiRes>

    @GET("/api/partidos/jugadores")
    suspend fun getMatchesJug(): Response<List<PartidoJugRes>>

    @GET("/api/partidos/equipo")
    suspend fun getMatchesEqui(): Response<List<PartidoEquiRes>>

    @POST("/api/partidos/jugadores/{idPartido}")
    suspend fun joinMatchJug(@Path("idPartido") idPartido: Int , @Body participacion: ParticipacionReq): Response<Unit>
    @POST("/api/partidos/equipo/{idPartido}")
    suspend fun joinMatchEqui(@Path("idPartido") idPartido: Int): Response<Unit>

    @POST("/api/partidos/cancelar/{idPartido}")
    suspend fun leaveMatch(@Path("idPartido") idPartido: Int): Response<Unit>

    @GET("/api/partidos/usuario/jugadores")
    suspend fun getMyMatchesJug(): Response<List<PartidoJugRes>>

    @GET("/api/partidos/usuario/equipo")
    suspend fun getMyMatchesEqui(): Response<List<PartidoEquiRes>>

    @GET("/api/participaciones/usuario")
    suspend fun getMyParticipations(): Response<List<ParticipacionRes>>

    @POST("/api/partidos/descartar/{idPartido}")
    suspend fun discardMatch(@Path("idPartido") idPartido: Int): Response<Unit>
}