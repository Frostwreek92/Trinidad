package com.example.trinidad.data.remote.api

import com.example.trinidad.data.remote.dto.LeaguesResponseDto
import com.example.trinidad.data.remote.dto.PlayersResponseDto
import com.example.trinidad.data.remote.dto.TeamDetailResponseDto
import com.example.trinidad.data.remote.dto.TeamsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {

    @GET("leagues")
    suspend fun getLeagues(): LeaguesResponseDto

    @GET("teams")
    suspend fun getTeamsByLeague(
        @Query("league") leagueId: Int,
        @Query("season") season: Int = 2023
    ): TeamsResponseDto

    @GET("teams")
    suspend fun getTeamDetail(
        @Query("id") teamId: Int
    ): TeamDetailResponseDto

    @GET("players")
    suspend fun getPlayersByTeam(
        @Query("team") teamId: Int,
        @Query("season") season: Int
    ): PlayersResponseDto

    @GET("players")
    suspend fun getPlayerDetail(
        @Query("id") playerId: Int,
        @Query("season") season: Int
    ): PlayersResponseDto


}