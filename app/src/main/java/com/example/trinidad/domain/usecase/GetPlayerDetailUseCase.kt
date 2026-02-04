package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.PlayerDetail
import com.example.trinidad.domain.repository.PlayerRepository

class GetPlayerDetailUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(playerId: Int): PlayerDetail {
        return repository.getPlayerDetail(playerId)
    }
}
