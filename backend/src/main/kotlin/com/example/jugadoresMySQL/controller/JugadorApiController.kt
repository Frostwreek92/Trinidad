package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/jugadores")
@CrossOrigin(origins = ["*"]) // Permitir peticiones desde Android
class JugadorApiController(
    private val jugadorService: JugadorService
) {

    @GetMapping
    fun getAllJugadores(): ResponseEntity<List<Jugador>> {
        val jugadores = jugadorService.findAll()
        return ResponseEntity.ok(jugadores)
    }

    @GetMapping("/{id}")
    fun getJugadorById(@PathVariable id: Int): ResponseEntity<Jugador> {
        return try {
            val jugador = jugadorService.findById(id)
            ResponseEntity.ok(jugador)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createJugador(@RequestBody jugador: Jugador): ResponseEntity<Jugador> {
        val nuevoJugador = jugadorService.save(jugador)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJugador)
    }

    @PutMapping("/{id}")
    fun updateJugador(@PathVariable id: Int, @RequestBody jugador: Jugador): ResponseEntity<Jugador> {
        return try {
            jugadorService.findById(id) // Verificar que existe
            jugador.idJugador = id
            val jugadorActualizado = jugadorService.save(jugador)
            ResponseEntity.ok(jugadorActualizado)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteJugador(@PathVariable id: Int): ResponseEntity<Void> {
        return try {
            jugadorService.findById(id) // Verificar que existe
            jugadorService.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/equipo/{equipo}")
    fun getJugadoresByEquipo(@PathVariable equipo: String): ResponseEntity<List<Jugador>> {
        val jugadores = jugadorService.findAll().filter { 
            it.nombreEquipo?.equals(equipo, ignoreCase = true) == true 
        }
        return ResponseEntity.ok(jugadores)
    }

    @GetMapping("/posicion/{posicion}")
    fun getJugadoresByPosicion(@PathVariable posicion: String): ResponseEntity<List<Jugador>> {
        val jugadores = jugadorService.findAll().filter { 
            it.posicion?.equals(posicion, ignoreCase = true) == true 
        }
        return ResponseEntity.ok(jugadores)
    }

    @GetMapping("/estadisticas")
    fun getEstadisticas(): ResponseEntity<Map<String, Any>> {
        val jugadores = jugadorService.findAll()
        val estadisticas: Map<String, Any> = mapOf(
            "totalJugadores" to jugadores.size,
            "totalEquipos" to jugadores.mapNotNull { it.nombreEquipo }.distinct().size,
            "jugadoresJovenes" to jugadores.count { it.edad?.let { edad -> edad in 18..25 } == true },
            "jugadoresVeteranos" to jugadores.count { it.edad?.let { edad -> edad in 26..30 } == true },
            "jugadoresExpertos" to jugadores.count { it.edad?.let { edad -> edad > 30 } == true },
            "posiciones" to jugadores.mapNotNull { it.posicion }.distinct(),
            "equipos" to jugadores.mapNotNull { it.nombreEquipo }.distinct()
        )
        return ResponseEntity.ok(estadisticas)
    }

    @PostMapping("/importar")
    fun importarJugadores(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, Any>> {
        return try {
            if (file.isEmpty) {
                val errorResponse: Map<String, Any> = mapOf(
                    "error" to "El archivo está vacío",
                    "message" to "Por favor, selecciona un archivo CSV válido"
                )
                return ResponseEntity.badRequest().body(errorResponse)
            }

            val count = jugadorService.importarDesdeCsv(file)
            val successResponse: Map<String, Any> = mapOf(
                "message" to "Se importaron $count jugadores correctamente",
                "count" to count,
                "status" to "success"
            )
            ResponseEntity.ok(successResponse)
        } catch (ex: Exception) {
            val errorResponse: Map<String, Any> = mapOf(
                "error" to "Error al importar el CSV",
                "message" to (ex.message ?: "Error desconocido"),
                "status" to "error"
            )
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
        }
    }
}
