package com.example.partidoya.Service

import com.example.partidoya.domain.Option
import com.example.partidoya.dto.res.UserProfileRes
import retrofit2.Response
import retrofit2.http.GET

interface OptionsService {
    @GET("api/options/positions")
    suspend fun getPositionOptions(): Response<List<Option>>
    @GET("api/options/playstyles")
    suspend fun getPlayStyleOptions(): Response<List<Option>>
}