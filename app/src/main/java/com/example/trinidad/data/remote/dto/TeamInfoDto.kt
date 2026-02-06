package com.example.trinidad.data.remote.dto

data class TeamInfoDto(
    val id: Int,
    val name: String,
    val code: String?,
    val country: String,
    val founded: Int?,
    val national: Boolean,
    val logo: String
)
