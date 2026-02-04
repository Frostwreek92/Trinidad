package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.TeamDetail
import com.example.trinidad.domain.repository.TeamRepository

class GetTeamDetailUseCase(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(teamId: Int): TeamDetail =
        repository.getTeamDetail(teamId)
}
