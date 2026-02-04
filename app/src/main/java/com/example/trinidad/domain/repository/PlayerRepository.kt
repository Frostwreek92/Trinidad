package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail

interface PlayerRepository {

    suspend fun getPlayersByTeam(teamId: Int): List<Player>

    suspend fun getPlayerDetail(playerId: Int): PlayerDetail
}



