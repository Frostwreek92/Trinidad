package com.example.trinidad.domain.usecase.api

import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.repository.api.PlayerRepository

class GetPlayersByTeamUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(teamId: Int): List<Player> {
        return repository.getPlayersByTeam(teamId)
    }
}