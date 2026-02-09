package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.repository.LeagueRepositoryImpl
import com.example.trinidad.data.repository.TeamRepositoryImpl
import com.example.trinidad.domain.usecase.GetLeaguesUseCase
import com.example.trinidad.domain.usecase.GetTeamsByLeagueUseCase

class LigasViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val leagueRepository = LeagueRepositoryImpl(ApiProvider)
        val teamRepository = TeamRepositoryImpl(ApiProvider)
        val getLeaguesUseCase = GetLeaguesUseCase(leagueRepository)
        val getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(teamRepository)
        return LigasViewModel(
            getLeaguesUseCase = getLeaguesUseCase,
            getTeamsByLeague = getTeamsByLeagueUseCase
        ) as T
    }
}
