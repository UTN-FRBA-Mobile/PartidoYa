package com.example.partidoya.Service


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // Para parsear automágicamente el json
            .baseUrl("https://partido-ya-backend.onrender.com/") // la URL
            .build()
    val footballFieldsService = retrofit.create(FootballFieldsService::class.java)

    val userService = retrofit.create(UserService::class.java)


}