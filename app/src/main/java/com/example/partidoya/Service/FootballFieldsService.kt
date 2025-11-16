package com.example.partidoya.Service

import com.example.partidoya.domain.Barrio
import com.example.partidoya.domain.Cancha
import com.example.partidoya.dto.req.ParticipacionReq
import com.example.partidoya.dto.req.PartidoReq
import com.example.partidoya.dto.res.ParticipacionRes
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
    suspend fun newMatch(@Body partido: PartidoReq): Response<PartidoRes>

    @GET("/api/partidos/jugadores/competitivo")
    suspend fun getMatchesJugComp(): Response<List<PartidoRes>>
    @GET("/api/partidos/jugadores/recreativo")
    suspend fun getMatchesJugRec(): Response<List<PartidoRes>>

    @GET("/api/partidos/equipo/competitivo")
    suspend fun getMatchesEquiComp(): Response<List<PartidoRes>>
    @GET("/api/partidos/equipo/recreativo")
    suspend fun getMatchesEquiRec(): Response<List<PartidoRes>>

    @POST("/api/partidos/jugadores/{idPartido}")
    suspend fun joinMatchJug(@Path("idPartido") idPartido: Int , @Body participacion: ParticipacionReq): Response<Unit>
    @POST("/api/partidos/equipo/{idPartido}")
    suspend fun joinMatchEqui(@Path("idPartido") idPartido: Int): Response<Unit>

    @POST("/api/partidos/cancelar/{idPartido}")
    suspend fun leaveMatch(@Path("idPartido") idPartido: Int): Response<Unit>

    @GET("/api/partidos/usuario/jugadores")
    suspend fun getMyMatchesJug(): Response<List<PartidoRes>>

    @GET("/api/partidos/usuario/equipo")
    suspend fun getMyMatchesEqui(): Response<List<PartidoRes>>

    @GET("/api/participaciones/usuario")
    suspend fun getMyParticipations(): Response<List<ParticipacionRes>>

    @POST("/api/partidos/descartar/{idPartido}")
    suspend fun discardMatch(@Path("idPartido") idPartido: Int): Response<Unit>

    @GET("/api/partidos/usuario/jugador")
    suspend fun getMyMatchesAsPlayer(): Response<List<PartidoRes>>

    @GET("/api/partidos/usuario/organizador")
    suspend fun getMyMatchesAsOrganizer(): Response<List<PartidoRes>>

    @POST("/api/partidos/suspender/{idPartido}")
    suspend fun suspendMatch(@Path("idPartido") idPartido: Int): Response<Unit>

    //IMPORTANTE: Ciro cambio esto que originalmente estaba como string
    //Esto fue porque el enpoint de barrios devuelve un barrio
    @GET("/api/barrios")
    suspend fun getBarrios(): Response<List<Barrio>>


}