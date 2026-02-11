package com.example.trinidad.domain.usecase.local

import com.example.trinidad.domain.repository.local.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteFormacionLegendariaUseCase @Inject constructor(
    private val repository: EquipoLegendarioRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.deleteFormacion()
    }
}
