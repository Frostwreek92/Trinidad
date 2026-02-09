package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.player.ApiFootballPlayerMapper
import com.example.trinidad.data.mapper.player.FootballDataPlayerMapper
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail
import com.example.trinidad.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val apiProvider: ApiProvider
) : PlayerRepository {

    override suspend fun getPlayersByTeam(teamId: Int): List<Player> {
        return when (apiProvider.currentApi) {
            ApiType.API_FOOTBALL -> {
                apiProvider.apiFootball
                    .getPlayersByTeam(teamId, season = 2023)
                    .response
                    .map { ApiFootballPlayerMapper.fromListItem(it) }
            }
            ApiType.FOOTBALL_DATA -> {
                apiProvider.footballData
                    .getTeamSquad(teamId)
                    .squad
                    .map { FootballDataPlayerMapper.fromSquadItem(it) }
            }
            else -> emptyList()
        }
    }

    override suspend fun getPlayerDetail(playerId: Int): PlayerDetail {
        return when (apiProvider.currentApi) {
            ApiType.API_FOOTBALL -> {
                val item = apiProvider.apiFootball
                    .getPlayerDetail(playerId)
                    .response
                    .first()
                ApiFootballPlayerMapper.toDetail(item)
            }
            ApiType.FOOTBALL_DATA -> {
                val item = apiProvider.footballData
                    .getPlayer(playerId)
                FootballDataPlayerMapper.toDetail(item)
            }
            else -> throw IllegalStateException("API no soportada")
        }
    }
}
