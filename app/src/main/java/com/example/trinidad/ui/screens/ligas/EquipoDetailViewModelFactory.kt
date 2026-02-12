package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.repository.PlayerRepositoryImpl
import com.example.trinidad.data.repository.TeamRepositoryImpl
import com.example.trinidad.domain.usecase.api.GetPlayersByTeamUseCase
import com.example.trinidad.domain.usecase.api.GetTeamDetailUseCase

class EquipoDetailViewModelFactory(
    private val teamId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val teamRepository = TeamRepositoryImpl(ApiProvider)
        val playerRepository = PlayerRepositoryImpl(ApiProvider)
        val getTeamDetailUseCase = GetTeamDetailUseCase(teamRepository)
        val getPlayersByTeamUseCase = GetPlayersByTeamUseCase(playerRepository)
        return EquipoDetailViewModel(
            teamId = teamId,
            getTeamDetailUseCase = getTeamDetailUseCase,
            getPlayersByTeamUseCase = getPlayersByTeamUseCase
        ) as T
    }
}
