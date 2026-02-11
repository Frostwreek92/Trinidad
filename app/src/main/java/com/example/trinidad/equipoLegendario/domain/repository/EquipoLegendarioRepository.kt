package com.example.trinidad.equipoLegendario.domain.repository

import com.example.trinidad.domain.model.Player
import com.example.trinidad.equipoLegendario.domain.model.Formacion

interface EquipoLegendarioRepository {
    suspend fun getFormacion(): Formacion?
    suspend fun saveFormacion(formacion: Formacion): Formacion
    suspend fun deleteFormacion(): Boolean
    suspend fun existsFormacion(): Boolean
    suspend fun getAllJugadores(): List<Player>
}