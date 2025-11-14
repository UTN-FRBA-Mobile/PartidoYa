package com.example.partidoya.Service

import com.example.partidoya.dto.res.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun registerUser(@Body request: LoginRequest): LoginResponse
    @POST("api/auth/google_login")
    suspend fun googleLogin(@Body request: GoogleLoginRequest): GoogleLoginResponse
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class GoogleLoginRequest(
    val googleToken: String
)

data class  GoogleLoginResponse(
    val token: String,
    val register: Boolean
)