package com.example.trinidad.data.remote.dto

data class TeamDetailResponseDto(
    val response: List<TeamDetailDto>
)

data class TeamDetailDto(
    val team: TeamInfoDto,
    val venue: VenueDto
)

data class VenueDto(
    val name: String,
    val city: String,
    val capacity: Int
)