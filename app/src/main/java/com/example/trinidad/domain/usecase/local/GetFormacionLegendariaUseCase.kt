package com.example.trinidad.domain.usecase.local

import com.example.trinidad.domain.model.local.Formacion
import com.example.trinidad.domain.repository.local.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFormacionLegendariaUseCase @Inject constructor(
    private val repository: EquipoLegendarioRepository
) {
    suspend operator fun invoke(): Formacion? {
        return repository.getFormacion()
    }
}
