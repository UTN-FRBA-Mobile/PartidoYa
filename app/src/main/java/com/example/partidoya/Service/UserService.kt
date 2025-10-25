package com.example.partidoya.Service

import com.example.partidoya.domain.ApiResponse
import com.example.partidoya.domain.Jugador
import com.example.partidoya.dto.req.UserProfileReq
import com.example.partidoya.dto.res.UserProfileRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("api/user/profile")
    suspend fun getProfileData(): Response<UserProfileRes>

    @POST("api/user/profile")
    suspend fun updateProfileData(@Body profileData: UserProfileReq): Response<ApiResponse>

    @POST("api/v1/auth/logout")
    suspend fun logoutUser(): Response<Unit>
}