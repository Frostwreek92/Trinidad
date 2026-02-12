package com.example.trinidad.domain.usecase.api

import com.example.trinidad.domain.model.api.TeamDetail
import com.example.trinidad.domain.repository.api.TeamRepository

class GetTeamDetailUseCase(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(teamId: Int): TeamDetail =
        repository.getTeamDetail(teamId)
}