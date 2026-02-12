package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.api.FootballDataApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballDataNetworkModule {

    private const val BASE_URL = "https://api.football-data.org/v4/"
    private const val API_KEY = "6fc6b168035441378048ee6f0ddd5d10"

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("X-Auth-Token", API_KEY)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun provideApi(): FootballDataApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataApi::class.java)
    }
}
