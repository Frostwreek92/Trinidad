package com.example.trinidad.data.remote.api

import com.example.trinidad.data.remote.dto.apifootball.LeaguesResponseDto
import com.example.trinidad.data.remote.dto.apifootball.PlayersResponseDto
import com.example.trinidad.data.remote.dto.apifootball.TeamsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFootballApi {

    // ────────────────
    // LIGAS
    // ────────────────
    @GET("leagues")
    suspend fun getLeagues(): LeaguesResponseDto


    // ────────────────
    // EQUIPOS
    // ────────────────
    @GET("teams")
    suspend fun getTeamsByLeague(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): TeamsResponseDto


    // ────────────────
    // DETALLE EQUIPO
    // ────────────────
    @GET("teams")
    suspend fun getTeamDetail(
        @Query("id") teamId: Int
    ): TeamsResponseDto


    // ────────────────
    // JUGADORES
    // ────────────────
    @GET("players")
    suspend fun getPlayersByTeam(
        @Query("team") teamId: Int,
        @Query("season") season: Int = 2023
    ): PlayersResponseDto


    // ────────────────
    // DETALLE JUGADOR
    // ────────────────
    @GET("players")
    suspend fun getPlayerDetail(
        @Query("id") playerId: Int,
        @Query("season") season: Int = 2023
    ): PlayersResponseDto

}
