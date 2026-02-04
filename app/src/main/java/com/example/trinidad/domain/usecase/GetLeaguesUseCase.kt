package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.repository.LeagueRepository

class GetLeaguesUseCase(
    private val repository: LeagueRepository
) {
    suspend operator fun invoke() =
        repository.getLeagues()
}