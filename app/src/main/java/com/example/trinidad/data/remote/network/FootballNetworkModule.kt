package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.api.FootballApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballNetworkModule {

    private const val BASE_URL = "https://v3.football.api-sports.io/"
    private const val API_KEY = "a760a5e65776e9ee8548dd1b098fec2e"

    fun provideApi(): FootballApi {

        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("x-apisports-key", API_KEY)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballApi::class.java)
    }
}
