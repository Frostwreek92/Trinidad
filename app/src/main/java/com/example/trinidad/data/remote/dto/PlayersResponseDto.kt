package com.example.trinidad.data.remote.dto

data class PlayersResponseDto(
    val response: List<PlayerDto>
)

data class PlayerDto(
    val player: PlayerInfoDto
)

data class PlayerInfoDto(
    val id: Int,
    val name: String,
    val age: Int,
    val nationality: String,
    val photo: String,
    val position: String
)