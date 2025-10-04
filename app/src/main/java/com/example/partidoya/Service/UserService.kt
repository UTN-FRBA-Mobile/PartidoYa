package com.example.partidoya.Service

import com.example.partidoya.domain.ApiResponse
import com.example.partidoya.domain.Jugador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("api/user/profile")
    suspend fun getProfileData(): Response<Jugador>

    @POST("api/user/profile")
    suspend fun updateProfileData(@Body profileData: Jugador): Response<ApiResponse>
}