package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.local.EquipoLegendarioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // ⚠️ CAMBIA si tu backend usa otro puerto
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideEquipoLegendarioApi(
        retrofit: Retrofit
    ): EquipoLegendarioApi {
        return retrofit.create(EquipoLegendarioApi::class.java)
    }
}
