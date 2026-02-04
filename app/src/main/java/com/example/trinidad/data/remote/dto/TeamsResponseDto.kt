package com.example.trinidad.data.remote.dto

data class TeamsResponseDto(
    val response: List<TeamDto>
)

data class TeamDto(
    val team: TeamInfoDto
)
