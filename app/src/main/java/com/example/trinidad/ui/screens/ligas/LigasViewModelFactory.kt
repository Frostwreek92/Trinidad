package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.equipoLegendario.data.remote.provideFootballApi
import com.example.trinidad.data.repository.LeagueRepositoryImpl
import com.example.trinidad.data.repository.TeamRepositoryImpl
import com.example.trinidad.domain.usecase.GetLeaguesUseCase
import com.example.trinidad.domain.usecase.GetTeamsByLeagueUseCase

class LigasViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val api = provideFootballApi()

        val leagueRepository = LeagueRepositoryImpl(api)
        val teamRepository = TeamRepositoryImpl(api)

        val getLeaguesUseCase = GetLeaguesUseCase(leagueRepository)
        val getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(teamRepository)

        return LigasViewModel(
            getLeaguesUseCase,
            getTeamsByLeagueUseCase
        ) as T
    }
}
