package com.example.trinidad.domain.repository.api

import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.model.api.PlayerDetail

interface PlayerRepository {

    suspend fun getPlayersByTeam(teamId: Int): List<Player>

    suspend fun getPlayerDetail(playerId: Int): PlayerDetail
}