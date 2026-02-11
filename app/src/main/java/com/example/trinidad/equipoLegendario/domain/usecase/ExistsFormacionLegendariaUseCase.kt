package com.example.trinidad.equipoLegendario.domain.usecase

import com.example.trinidad.equipoLegendario.domain.repository.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExistsFormacionLegendariaUseCase @Inject constructor(
    private val repository: EquipoLegendarioRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.existsFormacion()
    }
}
