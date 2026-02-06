package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.ApiProvider.provideFootballApi
import com.example.trinidad.data.repository.PlayerRepositoryImpl
import com.example.trinidad.data.repository.TeamRepositoryImpl
import com.example.trinidad.domain.usecase.GetPlayersByTeamUseCase
import com.example.trinidad.domain.usecase.GetTeamDetailUseCase

class EquipoDetailViewModelFactory(
    private val teamId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val api = provideFootballApi()

        val teamRepository = TeamRepositoryImpl(api)
        val playerRepository = PlayerRepositoryImpl(api)

        val teamUseCase = GetTeamDetailUseCase(teamRepository)
        val playersUseCase = GetPlayersByTeamUseCase(playerRepository)

        return EquipoDetailViewModel(
            teamId,
            teamUseCase,
            playersUseCase
        ) as T

    }
}
