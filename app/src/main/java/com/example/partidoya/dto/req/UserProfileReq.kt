package com.example.partidoya.dto.req

data class UserProfileReq (
    val name: String,
    val surname: String,
    val userIdentifier: String,
    val location: String,
    //val playStyle: String,
    val preferedPosition: String,
    val description: String,
    val celular: String
)


