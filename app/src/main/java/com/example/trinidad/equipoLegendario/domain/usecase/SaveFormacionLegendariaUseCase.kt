package com.example.trinidad.equipoLegendario.domain.usecase

import com.example.trinidad.equipoLegendario.domain.model.Formacion
import com.example.trinidad.equipoLegendario.domain.repository.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveFormacionLegendariaUseCase @Inject constructor(
    private val repository: EquipoLegendarioRepository
) {
    suspend operator fun invoke(formacion: Formacion): Formacion {
        return repository.saveFormacion(formacion)
    }
}
