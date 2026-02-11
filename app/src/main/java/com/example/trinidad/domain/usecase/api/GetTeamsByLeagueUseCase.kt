package com.example.trinidad.domain.usecase.api

import com.example.trinidad.domain.repository.api.TeamRepository

class GetTeamsByLeagueUseCase(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(leagueId: Int) =
        repository.getTeamsByLeague(leagueId)
}