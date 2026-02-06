package com.example.trinidad.domain.model

data class TeamDetail(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: String,
    val national: Boolean,
    val logo: String,

    val stadium: String,
    val stadiumCity: String,
    val stadiumAddress: String,
    val capacity: String,
    val surface: String,
    val stadiumImage: String
)

