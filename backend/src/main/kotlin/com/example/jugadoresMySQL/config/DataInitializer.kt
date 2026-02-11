package com.example.jugadoresMySQL.config

import com.example.jugadoresMySQL.model.Equipo
import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.model.JugadorPorEquipo
import com.example.jugadoresMySQL.model.Liga
import com.example.jugadoresMySQL.service.EquipoPorLigaService
import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import com.example.jugadoresMySQL.service.JugadorService
import com.example.jugadoresMySQL.service.LigaService
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val equipoService: EquipoService,
    private val ligaService: LigaService,
    private val jugadorService: JugadorService,
    private val jugadorPorEquipoService: JugadorPorEquipoService,
    private val equipoPorLigaService: EquipoPorLigaService
    ) {

    fun crearRelacionesEquiposLigas() {
        val ligas = ligaService.findAll()
        val equipos = equipoService.findAll()

        ligas.forEach { liga ->
            // Asignar cada equipo a su liga correspondiente según el país
            val equiposDeLiga = equipos.filter { it.paisEquipo == liga.paisLiga }
            equiposDeLiga.forEach { equipo ->
                val relacion = com.example.jugadoresMySQL.model.EquipoPorLiga(
                    equipo = equipo,
                    liga = liga,
                    temporada = "2024-2025",
                    activo = true
                )
                equipoPorLigaService.save(relacion)
            }
        }
    }

    fun crearRelacionesJugadoresEquipos() {
        val jugadores = jugadorService.findAll()
        val equipos = equipoService.findAll()

        // Asignar jugadores a equipos según el id del equipo
        jugadores.forEach { jugador ->
            val equipo = equipos.find { it.idEquipo == jugador.idEquipo }
            if (equipo != null) {
                val relacion = JugadorPorEquipo(
                    jugador = jugador,
                    equipo = equipo,
                    dorsal = jugador.dorsal
                )
                jugadorPorEquipoService.save(relacion)
            }
        }
    }
}
