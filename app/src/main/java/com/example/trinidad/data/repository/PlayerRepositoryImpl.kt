package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.toDomain
import com.example.trinidad.data.mapper.toDetailDomain
import com.example.trinidad.data.remote.api.FootballApi
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail
import com.example.trinidad.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val api: FootballApi
) : PlayerRepository {

    override suspend fun getPlayersByTeam(teamId: Int): List<Player> {
        return api.getPlayersByTeam(
            teamId = teamId,
            season = 2023
        ).response.map { it.toDomain() }
    }

    override suspend fun getPlayerDetail(playerId: Int): PlayerDetail {
        return api.getPlayerDetail(
            playerId = playerId,
            season = 2023
        ).response.first().toDetailDomain()
    }
}