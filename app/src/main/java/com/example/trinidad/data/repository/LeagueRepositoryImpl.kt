package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.toDomain
import com.example.trinidad.data.remote.api.FootballApi
import com.example.trinidad.domain.model.League
import com.example.trinidad.domain.repository.LeagueRepository

class LeagueRepositoryImpl(
    private val api: FootballApi
) : LeagueRepository {

    override suspend fun getLeagues(): List<League> {
        return api.getLeagues()
            .response
            .map { it.toDomain() }
    }
}