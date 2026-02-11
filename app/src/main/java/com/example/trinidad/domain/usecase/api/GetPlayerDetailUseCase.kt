package com.example.trinidad.domain.usecase.api

import com.example.trinidad.domain.model.api.PlayerDetail
import com.example.trinidad.domain.repository.api.PlayerRepository

class GetPlayerDetailUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(playerId: Int): PlayerDetail {
        return repository.getPlayerDetail(playerId)
    }
}