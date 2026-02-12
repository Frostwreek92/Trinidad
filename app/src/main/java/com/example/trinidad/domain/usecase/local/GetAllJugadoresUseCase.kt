package com.example.trinidad.domain.usecase.local

import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.repository.local.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllJugadoresUseCase @Inject constructor(
    private val repository: EquipoLegendarioRepository
) {
    suspend operator fun invoke(): List<Player> {
        return repository.getAllJugadores()
    }
}
