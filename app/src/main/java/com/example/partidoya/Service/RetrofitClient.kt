package com.example.partidoya.Service


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val footballFieldsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()) // Para parsear automágicamente el json
        .baseUrl("https://partido-ya-backend.onrender.com/") // la URL
        .build()
        .create(FootballFieldsService::class.java)
}