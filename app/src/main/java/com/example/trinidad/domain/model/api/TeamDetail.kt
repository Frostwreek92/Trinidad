package com.example.trinidad.domain.model.api

data class TeamDetail(
    val id: Int,
    val name: String,
    val logo: String,
    val stadium: String,
    val city: String,
    val capacity: Int,
    val address: String,
    val surface: String,
    val stadiumImage: String
)