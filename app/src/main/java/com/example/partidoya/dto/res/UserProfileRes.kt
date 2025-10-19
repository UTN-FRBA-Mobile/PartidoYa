package com.example.partidoya.dto.res

data class UserProfileRes(
    val id: Long,
    val name: String,
    val surname: String,
    val userIdentifier: String,
    val location: String,
    val playStyle: String,
    val preferedPosition: String,
    val description: String
)