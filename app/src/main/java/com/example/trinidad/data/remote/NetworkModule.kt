package com.example.trinidad.data.remote

import com.example.trinidad.equipoLegendario.data.repository.EquipoLegendarioRepositoryImpl
import com.example.trinidad.equipoLegendario.domain.repository.EquipoLegendarioRepository
import com.example.trinidad.equipoLegendario.data.remote.api.EquipoLegendarioApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import javax.inject.Qualifier
import javax.inject.Singleton

private const val FOOTBALL_API_BASE_URL = "https://v3.football.api-sports.io/"
private const val FOOTBALL_API_KEY = "a760a5e65776e9ee8548dd1b098fec2e"

// CAMBIA ESTA LÍNEA según tu entorno:
// Para emulador Android: "http://10.0.2.2:8080/"
// Para dispositivo físico: "http://192.168.0.22:8080/" (tu IP local)
private const val LOCAL_API_BASE_URL = "http://10.0.2.2:8080/" // CAMBIAR según necesidad

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FootballApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalApi

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        @FootballApi
        fun provideFootballApi(): com.example.trinidad.data.remote.api.FootballApi {

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
                .create(com.example.trinidad.data.remote.api.FootballApi::class.java)
        }

        @Provides
        @Singleton
        @LocalApi
        fun provideLocalApi(): EquipoLegendarioApi {
            println("Usando URL base para API local: $LOCAL_API_BASE_URL")
            
            return Retrofit.Builder()
                .baseUrl(LOCAL_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EquipoLegendarioApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindEquipoLegendarioRepository(
        equipoLegendarioRepositoryImpl: EquipoLegendarioRepositoryImpl
    ): EquipoLegendarioRepository
}
