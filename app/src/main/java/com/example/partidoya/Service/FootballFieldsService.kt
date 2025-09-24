package com.example.partidoya.Service

import com.example.partidoya.domain.Cancha
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballFieldsService {
    @GET("/api/canchas")
    suspend fun list(): Response<List<Cancha>>
}