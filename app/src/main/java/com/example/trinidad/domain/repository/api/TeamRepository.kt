package com.example.trinidad.domain.repository.api

import com.example.trinidad.domain.model.api.Team
import com.example.trinidad.domain.model.api.TeamDetail

interface TeamRepository {
    suspend fun getTeamsByLeague(leagueId: Int): List<Team>
    suspend fun getTeamDetail(teamId: Int): TeamDetail
}