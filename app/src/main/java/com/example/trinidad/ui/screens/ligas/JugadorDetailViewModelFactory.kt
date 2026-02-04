package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.remote.provideFootballApi
import com.example.trinidad.data.repository.PlayerRepositoryImpl
import com.example.trinidad.domain.usecase.GetPlayerDetailUseCase

class JugadorDetailViewModelFactory(
    private val playerId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val api = provideFootballApi()
        val repository = PlayerRepositoryImpl(api)
        val useCase = GetPlayerDetailUseCase(repository)

        return JugadorDetailViewModel(playerId, useCase) as T
    }
}
