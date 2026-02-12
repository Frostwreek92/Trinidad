package com.example.trinidad.data.remote.dto.footballdata

data class CompetitionDto(
    val id: Int,
    val name: String,
    val emblem: String?,
    val area: AreaDto
)

data class AreaDto(
    val name: String
)
