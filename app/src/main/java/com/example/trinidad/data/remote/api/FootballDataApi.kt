package com.example.trinidad.data.remote.api

import com.example.trinidad.data.remote.dto.footballdata.CompetitionsResponseDto
import com.example.trinidad.data.remote.dto.footballdata.SquadPlayerDto
import com.example.trinidad.data.remote.dto.footballdata.SquadResponseDto
import com.example.trinidad.data.remote.dto.footballdata.TeamDto
import com.example.trinidad.data.remote.dto.footballdata.TeamsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballDataApi {

    @GET("competitions")
    suspend fun getCompetitions(): CompetitionsResponseDto

    @GET("competitions/{competitionId}/teams")
    suspend fun getTeamsByCompetition(
        @Path("competitionId") competitionId: Int
    ): TeamsResponseDto

    @GET("teams/{teamId}")
    suspend fun getTeamDetail(
        @Path("teamId") teamId: Int
    ): TeamDto

    @GET("teams/{id}")
    suspend fun getTeamSquad(
        @Path("id") teamId: Int
    ): SquadResponseDto

    @GET("persons/{id}")
    suspend fun getPlayer(
        @Path("id") playerId: Int
    ): SquadPlayerDto
}
