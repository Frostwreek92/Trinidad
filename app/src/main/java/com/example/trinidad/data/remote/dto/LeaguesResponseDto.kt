package com.example.trinidad.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LeaguesResponseDto(
    val response: List<LeagueDto>
)

data class LeagueDto(
    val league: LeagueInfoDto
)

data class LeagueInfoDto(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
)