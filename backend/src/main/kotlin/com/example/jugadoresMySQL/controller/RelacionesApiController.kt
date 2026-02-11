package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.EquipoPorLiga
import com.example.jugadoresMySQL.model.JugadorPorEquipo
import com.example.jugadoresMySQL.service.EquipoPorLigaService
import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import com.example.jugadoresMySQL.service.JugadorService
import com.example.jugadoresMySQL.service.LigaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/relaciones")
class RelacionesApiController(
    private val equipoPorLigaService: EquipoPorLigaService,
    private val jugadorPorEquipoService: JugadorPorEquipoService,
    private val equipoService: EquipoService,
    private val ligaService: LigaService,
    private val jugadorService: JugadorService
) {

    @PostMapping("/equipos-liga")
    fun crearRelacionEquipoLiga(
        @RequestParam idEquipo: Int,
        @RequestParam idLiga: Int,
        @RequestParam temporada: String = "2024-2025"
    ): ResponseEntity<String> {
        try {
            val equipo = try { equipoService.findById(idEquipo) } catch (e: Exception) { null }
            val liga = try { ligaService.findById(idLiga) } catch (e: Exception) { null }
            
            if (equipo == null || liga == null) {
                return ResponseEntity.badRequest().body("Equipo o liga no encontrados")
            }
            
            val relacion = EquipoPorLiga(
                equipo = equipo,
                liga = liga,
                temporada = temporada
            )
            
            equipoPorLigaService.save(relacion)
            return ResponseEntity.ok("Relación equipo-liga creada exitosamente")
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body("Error: ${e.message}")
        }
    }

    @PostMapping("/jugadores-equipo")
    fun crearRelacionJugadorEquipo(
        @RequestParam idJugador: Int,
        @RequestParam idEquipo: Int,
        @RequestParam dorsal: Int
    ): ResponseEntity<String> {
        try {
            val jugador = try { jugadorService.findById(idJugador) } catch (e: Exception) { null }
            val equipo = try { equipoService.findById(idEquipo) } catch (e: Exception) { null }
            
            if (jugador == null || equipo == null) {
                return ResponseEntity.badRequest().body("Jugador o equipo no encontrados")
            }
            
            val relacion = JugadorPorEquipo(
                jugador = jugador,
                equipo = equipo,
                dorsal = dorsal
            )
            
            jugadorPorEquipoService.save(relacion)
            return ResponseEntity.ok("Relación jugador-equipo creada exitosamente")
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body("Error: ${e.message}")
        }
    }

    @GetMapping("/equipos-por-liga/{idLiga}")
    fun getEquiposPorLiga(@PathVariable idLiga: Int): ResponseEntity<List<EquipoPorLiga>> {
        return ResponseEntity.ok(equipoPorLigaService.findByLiga(idLiga))
    }

    @GetMapping("/jugadores-por-equipo/{idEquipo}")
    fun getJugadoresPorEquipo(@PathVariable idEquipo: Int): ResponseEntity<List<JugadorPorEquipo>> {
        return ResponseEntity.ok(jugadorPorEquipoService.findByEquipo(idEquipo))
    }
}
