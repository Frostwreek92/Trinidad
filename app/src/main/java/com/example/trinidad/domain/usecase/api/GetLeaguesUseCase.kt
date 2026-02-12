package com.example.trinidad.domain.usecase.api

import com.example.trinidad.domain.repository.api.LeagueRepository

class GetLeaguesUseCase(
    private val repository: LeagueRepository
) {
    suspend operator fun invoke() =
        repository.getLeagues()
}