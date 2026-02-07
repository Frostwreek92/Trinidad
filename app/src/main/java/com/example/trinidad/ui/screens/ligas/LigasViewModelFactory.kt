package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiProvider.provideFootballApi
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.data.repository.LeagueRepositoryImpl
import com.example.trinidad.data.repository.TeamRepositoryImpl
import com.example.trinidad.domain.usecase.GetLeaguesUseCase
import com.example.trinidad.domain.usecase.GetTeamsByLeagueUseCase

class LigasViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val apiType = ApiType.API_FOOTBALL

        val leagueRepo = LeagueRepositoryImpl(
            apiType = apiType,
            apiFootballApi = ApiProvider.provideApiFootball(),
            footballDataApi = ApiProvider.provideFootballData()
        )

        val getLeaguesUseCase = GetLeaguesUseCase(leagueRepository)
        val getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(teamRepository)

        return LigasViewModel(
            getLeaguesUseCase,
            getTeamsByLeagueUseCase
        ) as T
    }
}
