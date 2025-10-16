package com.example.partidoya.Service


import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime

object RetrofitClient {

    @RequiresApi(Build.VERSION_CODES.O)
    val gson = GsonBuilder() //Para convertir datos desde String a LocalDate y LocalTime
        .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
            LocalDate.parse(json.asString)
        })
        .registerTypeAdapter(LocalTime::class.java, JsonDeserializer { json, _, _ ->
            LocalTime.parse(json.asString)
        })
        //Para convertir datos desde LocalDate y LocalTime a String
        .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { date, _, _ ->
            JsonPrimitive(date.toString()) // genera "2025-10-20"
        })
        .registerTypeAdapter(LocalTime::class.java, JsonSerializer<LocalTime> { time, _, _ ->
            JsonPrimitive(time.toString()) // genera "18:30"
        })
        .create()

    @RequiresApi(Build.VERSION_CODES.O)
    val footballFieldsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson)) // Para parsear automágicamente el json
        //.baseUrl("https://partido-ya-backend.onrender.com/") // la URL
        .baseUrl("http://IPLOCAL:8080/") // la URL
        .build()
        .create(FootballFieldsService::class.java)
}