package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorService
import com.example.jugadoresMySQL.service.LigaService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import com.example.jugadoresMySQL.service.EquipoPorLigaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/debug")
class DebugApiController(
    private val equipoService: EquipoService,
    private val jugadorService: JugadorService,
    private val ligaService: LigaService,
    private val jugadorPorEquipoService: JugadorPorEquipoService,
    private val equipoPorLigaService: EquipoPorLigaService
) {

    @GetMapping("/equipos")
    fun getEquipos(): ResponseEntity<Any> {
        val equipos = equipoService.findAll()
        return ResponseEntity.ok(mapOf(
            "count" to equipos.size,
            "data" to equipos
        ))
    }

    @GetMapping("/ligas")
    fun getLigas(): ResponseEntity<Any> {
        val ligas = ligaService.findAll()
        return ResponseEntity.ok(mapOf(
            "count" to ligas.size,
            "data" to ligas
        ))
    }

    @GetMapping("/jugadores")
    fun getJugadores(): ResponseEntity<Any> {
        val jugadores = jugadorService.findAll()
        return ResponseEntity.ok(mapOf(
            "count" to jugadores.size,
            "data" to jugadores
        ))
    }

    @GetMapping("/relaciones/jugadores-equipo")
    fun getRelacionesJugadoresEquipo(): ResponseEntity<Any> {
        val relaciones = jugadorPorEquipoService.findAll()
        return ResponseEntity.ok(mapOf(
            "count" to relaciones.size,
            "data" to relaciones
        ))
    }

    @GetMapping("/relaciones/equipos-liga")
    fun getRelacionesEquiposLiga(): ResponseEntity<Any> {
        val relaciones = equipoPorLigaService.findAll()
        return ResponseEntity.ok(mapOf(
            "count" to relaciones.size,
            "data" to relaciones
        ))
    }

    @PostMapping("/crear-datos-prueba")
    fun crearDatosPrueba(): ResponseEntity<String> {
        try {
            // Verificar si ya hay datos
            if (equipoService.findAll().isNotEmpty() && 
                ligaService.findAll().isNotEmpty() && 
                jugadorService.findAll().isNotEmpty()) {
                
                // Crear algunas relaciones de prueba
                val equipos = equipoService.findAll().take(3)
                val ligas = ligaService.findAll().take(2)
                val jugadores = jugadorService.findAll().take(5)

                // Crear relaciones equipos-liga
                equipos.forEachIndexed { index, equipo ->
                    val liga = ligas[index % ligas.size]
                    equipoPorLigaService.save(
                        com.example.jugadoresMySQL.model.EquipoPorLiga(
                            equipo = equipo,
                            liga = liga,
                            temporada = "2024-2025"
                        )
                    )
                }

                // Crear relaciones jugadores-equipo
                jugadores.forEachIndexed { index, jugador ->
                    val equipo = equipos[index % equipos.size]
                    jugadorPorEquipoService.save(
                        com.example.jugadoresMySQL.model.JugadorPorEquipo(
                            jugador = jugador,
                            equipo = equipo,
                            dorsal = index + 1
                        )
                    )
                }

                return ResponseEntity.ok("Datos de prueba creados exitosamente")
            } else {
                return ResponseEntity.badRequest().body("No hay suficientes datos principales (equipos, ligas, jugadores)")
            }
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body("Error: ${e.message}")
        }
    }
}
