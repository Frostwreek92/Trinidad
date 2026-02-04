package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.repository.PlayerRepository

class GetPlayersByTeamUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(teamId: Int): List<Player> {
        return repository.getPlayersByTeam(teamId)
    }
}
