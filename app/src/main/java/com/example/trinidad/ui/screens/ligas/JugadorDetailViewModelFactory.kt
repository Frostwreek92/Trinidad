package com.example.trinidad.ui.screens.ligas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trinidad.data.repository.PlayerRepositoryImpl
import com.example.trinidad.domain.usecase.GetPlayerDetailUseCase

class JugadorDetailViewModelFactory(
    private val playerId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JugadorDetailViewModel::class.java)) {

            val repository = PlayerRepositoryImpl()
            val getPlayerDetailUseCase = GetPlayerDetailUseCase(repository)

            return JugadorDetailViewModel(
                playerId = playerId,
                getPlayerDetailUseCase = getPlayerDetailUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
