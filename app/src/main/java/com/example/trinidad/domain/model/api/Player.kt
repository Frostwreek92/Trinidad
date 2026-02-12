package com.example.trinidad.domain.model.api

data class Player(
    val id: Int,
    val name: String,
    val position: String,
    val photo: String,
    val height: String?,
    val weight: String?,
    val nationality: String?
)