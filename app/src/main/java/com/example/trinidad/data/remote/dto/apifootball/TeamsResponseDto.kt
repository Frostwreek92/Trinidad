package com.example.trinidad.data.remote.dto.apifootball

data class TeamsResponseDto(
    val response: List<TeamResponseItemDto>
)

data class TeamResponseItemDto(
    val team: TeamDto,
    val venue: VenueDto?
)

data class TeamDto(
    val id: Int,
    val name: String?,
    val logo: String?
)
