package com.example.trinidad.data.remote.dto.apifootball

data class PlayersResponseDto(
    val response: List<PlayerResponseItemDto>
)

data class PlayerStatisticsDto(
    val games: GamesDto
)

data class GamesDto(
    val position: String?
)
