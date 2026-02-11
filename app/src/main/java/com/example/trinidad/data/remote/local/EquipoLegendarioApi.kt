package com.example.trinidad.data.remote.local

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface EquipoLegendarioApi {
    @GET("api/equipo-legendario")
    suspend fun getFormacionLegendaria(): FormacionDto?

    @POST("api/equipo-legendario")
    suspend fun saveFormacionLegendaria(@Body formacion: FormacionDto): FormacionDto

    @DELETE("api/equipo-legendario")
    suspend fun deleteFormacionLegendaria(): Unit

    @GET("api/equipo-legendario/exists")
    suspend fun existsFormacionLegendaria(): Boolean

    @GET("api/jugadores")
    suspend fun getJugadoresFromBackend(): List<BackendJugadorDto>
}