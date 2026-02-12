package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.api.ApiFootballApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFootballNetworkModule {

    private const val BASE_URL = "https://v3.football.api-sports.io/"
    private const val API_KEY = "a760a5e65776e9ee8548dd1b098fec2e"

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("x-apisports-key", API_KEY)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun provideApi(): ApiFootballApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballApi::class.java)
    }
}
