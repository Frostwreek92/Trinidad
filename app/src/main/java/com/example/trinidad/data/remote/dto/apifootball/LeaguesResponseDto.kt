package com.example.trinidad.data.remote.dto.apifootball

data class LeaguesResponseDto(
    val response: List<LeagueDto>
)

data class LeagueDto(
    val league: LeagueInfoDto,
    val country: CountryDto?
)

data class LeagueInfoDto(
    val id: Int,
    val name: String,
    val logo: String?
)

data class CountryDto(
    val name: String?
)
