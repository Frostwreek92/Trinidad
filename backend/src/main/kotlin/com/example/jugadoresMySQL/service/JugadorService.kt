package com.example.jugadoresMySQL

import org.springframework.stereotype.Service

@Service
class JugadorService(
    private val jugadorRepository: JugadorRepository
) {

    fun findAll(): List<Jugador> = jugadorRepository.findAll()

    fun findById(id: Int): Jugador =
        jugadorRepository.findById(id).orElseThrow { NoSuchElementException("Jugador con id $id no encontrado") }

    fun create(jugador: Jugador): Jugador = jugadorRepository.save(jugador)

    fun update(id: Int, updated: Jugador): Jugador {
        val existing = findById(id)

        existing.nombreJugador = updated.nombreJugador
        existing.nombreEquipo = updated.nombreEquipo
        existing.posicion = updated.posicion
        existing.edad = updated.edad
        existing.dorsal = updated.dorsal

        return jugadorRepository.save(existing)
    }

    fun delete(id: Int) {
        if (!jugadorRepository.existsById(id)) {
            throw NoSuchElementException("Jugador con id $id no encontrado")
        }
        jugadorRepository.deleteById(id)
    }
}
