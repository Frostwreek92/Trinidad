package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.League

interface LeagueRepository {
    suspend fun getLeagues(): List<League>
}