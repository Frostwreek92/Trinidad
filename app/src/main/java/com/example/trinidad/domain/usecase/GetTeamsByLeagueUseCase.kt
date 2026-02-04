package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.repository.TeamRepository

class GetTeamsByLeagueUseCase(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(leagueId: Int) =
        repository.getTeamsByLeague(leagueId)
}
