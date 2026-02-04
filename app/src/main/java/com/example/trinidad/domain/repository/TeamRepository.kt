package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail

interface TeamRepository {

    suspend fun getTeamsByLeague(leagueId: Int): List<Team>

    suspend fun getTeamDetail(teamId: Int): TeamDetail

}
