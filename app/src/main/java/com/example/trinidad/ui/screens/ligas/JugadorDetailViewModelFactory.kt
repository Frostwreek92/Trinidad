package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.repository.PlayerRepositoryImpl
import com.example.trinidad.domain.usecase.api.GetPlayerDetailUseCase

class JugadorDetailViewModelFactory(
    private val playerId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val playerRepository = PlayerRepositoryImpl(ApiProvider)
        val getPlayerDetailUseCase = GetPlayerDetailUseCase(playerRepository)
        return JugadorDetailViewModel(
            playerId = playerId,
            getPlayerDetailUseCase = getPlayerDetailUseCase
        ) as T
    }
}
