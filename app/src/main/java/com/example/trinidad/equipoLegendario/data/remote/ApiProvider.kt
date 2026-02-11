package com.example.trinidad.equipoLegendario.data.remote

import com.example.trinidad.data.remote.api.FootballApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FOOTBALL_API_BASE_URL = "https://v3.football.api-sports.io/"
private const val FOOTBALL_API_KEY = "a760a5e65776e9ee8548dd1b098fec2e"

fun provideFootballApi(): FootballApi {
    val interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("x-apisports-key", FOOTBALL_API_KEY)
            .build()
        chain.proceed(request)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl(FOOTBALL_API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FootballApi::class.java)
}
