package com.example.trinidad.domain.repository.local

import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.model.local.Formacion

interface EquipoLegendarioRepository {
    suspend fun getFormacion(): Formacion?
    suspend fun saveFormacion(formacion: Formacion): Formacion
    suspend fun deleteFormacion(): Boolean
    suspend fun existsFormacion(): Boolean
    suspend fun getAllJugadores(): List<Player>
}