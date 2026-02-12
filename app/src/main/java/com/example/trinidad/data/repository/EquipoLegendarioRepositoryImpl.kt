package com.example.trinidad.data.repository

import com.example.trinidad.data.mapper.local.BackendJugadorMapper
import com.example.trinidad.data.mapper.local.FormacionMapper
import com.example.trinidad.data.remote.local.EquipoLegendarioApi
import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.model.local.Formacion
import com.example.trinidad.domain.model.local.PosicionFormacion
import com.example.trinidad.domain.repository.local.EquipoLegendarioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipoLegendarioRepositoryImpl @Inject constructor(
    private val equipoLegendarioApi: EquipoLegendarioApi,
    private val backendJugadorMapper: BackendJugadorMapper
) : EquipoLegendarioRepository {

    override suspend fun getFormacion(): Formacion? {
        return try {
            val formacionDto = equipoLegendarioApi.getFormacionLegendaria()
            FormacionMapper.mapToDomain(formacionDto)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveFormacion(formacion: Formacion): Formacion {
        val formacionDto = FormacionMapper.mapToDto(formacion)
        val savedDto = equipoLegendarioApi.saveFormacionLegendaria(formacionDto)
        return FormacionMapper.mapToDomain(savedDto)!!
    }
    override suspend fun deleteFormacion(): Boolean {
        return try {
            equipoLegendarioApi.deleteFormacionLegendaria()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun existsFormacion(): Boolean {
        return try {
            equipoLegendarioApi.existsFormacionLegendaria()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getAllJugadores(): List<Player> {
        return try {
            println(" Cargando jugadores desde API...")
            val jugadoresDto = equipoLegendarioApi.getJugadoresFromBackend()
            println(" Se obtuvieron ${jugadoresDto.size} jugadores desde la API")
            
            val jugadores = backendJugadorMapper.mapToDomain(jugadoresDto)
            println(" Se mapearon ${jugadores.size} jugadores al dominio")
            
            jugadores
        } catch (e: Exception) {
            println(" Error al cargar jugadores: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    fun getDefaultFormationPositions(): List<PosicionFormacion> {
        return listOf(
            // Portero (centrado a la izquierda)
            PosicionFormacion(1, "POR", 0.1f, 0.5f),
            
            // Defensa (4) - línea vertical en el lado izquierdo
            PosicionFormacion(2, "DFC1", 0.25f, 0.2f),      // Defensa central superior
            PosicionFormacion(3, "DFC2", 0.25f, 0.4f),      // Defensa central
            PosicionFormacion(4, "DFC3", 0.25f, 0.6f),      // Defensa central
            PosicionFormacion(5, "DFC4", 0.25f, 0.8f),      // Defensa central inferior
            
            // Mediocampo (3) - línea vertical en el centro
            PosicionFormacion(6, "MC1", 0.5f, 0.25f),      // Mediocampo superior
            PosicionFormacion(7, "MC2", 0.5f, 0.5f),       // Mediocampo central
            PosicionFormacion(8, "MC3", 0.5f, 0.75f),      // Mediocampo inferior
            
            // Delanteros (3) - línea vertical en el lado derecho
            PosicionFormacion(9, "EXT1", 0.75f, 0.3f),      // Extremo superior
            PosicionFormacion(10, "DEL", 0.8f, 0.5f),     // Delantero centro
            PosicionFormacion(11, "EXT2", 0.75f, 0.7f)      // Extremo inferior
        )
    }
}
