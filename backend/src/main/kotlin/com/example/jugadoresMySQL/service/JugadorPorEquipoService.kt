package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.JugadorPorEquipo
import com.example.jugadoresMySQL.repository.JugadorPorEquipoRepository
import org.springframework.stereotype.Service

@Service
class JugadorPorEquipoService(
    private val jugadorPorEquipoRepository: JugadorPorEquipoRepository
) {

    fun findAll(): List<JugadorPorEquipo> = jugadorPorEquipoRepository.findAll()

    fun findByEquipo(idEquipo: Int): List<JugadorPorEquipo> = 
        jugadorPorEquipoRepository.findByEquipo(idEquipo)

    fun save(jugadorPorEquipo: JugadorPorEquipo): JugadorPorEquipo = 
        jugadorPorEquipoRepository.save(jugadorPorEquipo)

    fun delete(id: Int) {
        if (!jugadorPorEquipoRepository.existsById(id)) {
            throw NoSuchElementException("Relación jugador-equipo con id $id no encontrada")
        }
        jugadorPorEquipoRepository.deleteById(id)
    }
}
