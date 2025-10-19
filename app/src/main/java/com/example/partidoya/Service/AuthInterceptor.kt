package com.example.partidoya.Service

import android.content.Context
import com.example.partidoya.Preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       val preferences = Preferences(context)
        val token = preferences.getToken()

        val requestBuilder = chain.request().newBuilder()
        if(!token.isNullOrEmpty()){
           requestBuilder.addHeader("Authorization","Bearer $token")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}