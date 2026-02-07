package com.example.trinidad.data.remote.dto.apifootball

data class PlayersResponseDto(
    val response: List<PlayerResponseItemDto>
)

data class PlayerResponseItemDto(
    val player: PlayerDto,
    val statistics: List<PlayerStatisticsDto>
)

data class PlayerDto(
    val id: Int,
    val name: String?,
    val photo: String?
)

data class PlayerStatisticsDto(
    val games: GamesDto
)

data class GamesDto(
    val position: String?
)
