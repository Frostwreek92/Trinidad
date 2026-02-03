package com.example.controller.jugadoresMySQL

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/jugadores")
class JugadorController(
    private val jugadorService: JugadorService
) {

    @GetMapping
    fun getAll(): List<Jugador> = jugadorService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Jugador> =
        try {
            ResponseEntity.ok(jugadorService.findById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody jugador: Jugador): Jugador = jugadorService.create(jugador)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody jugador: Jugador): ResponseEntity<Jugador> =
        try {
            ResponseEntity.ok(jugadorService.update(id, jugador))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Void> =
        try {
            jugadorService.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
}
