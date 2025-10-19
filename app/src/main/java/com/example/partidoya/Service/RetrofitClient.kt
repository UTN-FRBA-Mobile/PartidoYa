package com.example.partidoya.Service


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private var userServiceInstance: UserService? = null

    fun init(context: Context) {
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
        .build()
    retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // Para parsear automágicamente el json
            .baseUrl("http://10.0.2.2:8080/") // la URL
            .client(client)
            .build()
    //val footballFieldsService = retrofit.create(FootballFieldsService::class.java)

     userServiceInstance = retrofit!!.create(UserService::class.java)
}

    val userService: UserService
        get() = userServiceInstance
            ?: throw IllegalStateException("RetrofitClient not initialized. Call init(context) first.")

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
    val footballFieldsService: FootballFieldsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson)) // Para parsear automágicamente el json
        //.baseUrl("https://partido-ya-backend.onrender.com/") // la URL
        .baseUrl("http://localhost:8080/") // la URL
        .build()
        .create(FootballFieldsService::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    val authService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson)) // Para parsear automágicamente el json
        .baseUrl("http://10.0.2.2:8080/") // la URL
        .build()
        .create(AuthService::class.java)
}