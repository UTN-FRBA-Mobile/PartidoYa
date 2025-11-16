package com.example.partidoya.Service

import com.example.partidoya.domain.Barrio
import com.example.partidoya.domain.Option
import retrofit2.Response
import retrofit2.http.GET

interface BarrioService {
    @GET("api/barrios")
    suspend fun getBarrios(): Response<List<Barrio>>
}