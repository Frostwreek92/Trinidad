package com.example.trinidad.equipoLegendario.domain.usecase

import com.example.trinidad.domain.model.Player
import com.example.trinidad.equipoLegendario.domain.repository.EquipoLegendarioRepository
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
