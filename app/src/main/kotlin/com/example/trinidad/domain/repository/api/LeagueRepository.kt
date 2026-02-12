package com.example.trinidad.domain.repository.api

import com.example.trinidad.domain.model.api.League

interface LeagueRepository {
    suspend fun getLeagues(): List<League>
}