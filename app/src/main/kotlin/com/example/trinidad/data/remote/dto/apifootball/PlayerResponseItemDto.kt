package com.example.trinidad.data.remote.dto.apifootball

data class PlayerResponseItemDto(
    val player: PlayerDto,
    val statistics: List<PlayerStatisticsDto>
)
