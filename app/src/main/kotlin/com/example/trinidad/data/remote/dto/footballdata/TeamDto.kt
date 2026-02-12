package com.example.trinidad.data.remote.dto.footballdata

data class TeamDto(
    val id: Int,
    val name: String?,
    val shortName: String?,
    val crest: String?,
    val venue: String?,
    val area: AreaDto?
)