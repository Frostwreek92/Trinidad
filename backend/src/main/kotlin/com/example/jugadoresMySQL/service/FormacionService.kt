package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.Formacion
import com.example.jugadoresMySQL.repository.JugadorEnPosicionRepository
import com.example.jugadoresMySQL.repository.FormacionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FormacionService(
    private val formacionRepository: FormacionRepository,
    private val jugadorService: JugadorService,
    private val jugadorEnPosicionRepository: JugadorEnPosicionRepository
) {

    fun findAll(): List<Formacion> {
        return formacionRepository.findAll()
    }

    fun findById(id: Int): Formacion {
        return formacionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Formación no encontrada con id: $id") }
    }

    fun save(formacion: Formacion): Formacion {
        if (formacion.idFormacion != null) {
            val existingFormacion = formacionRepository.findById(formacion.idFormacion!!).orElse(null)
            if (existingFormacion != null) {
                jugadorEnPosicionRepository.deleteByFormacion(existingFormacion)
                jugadorEnPosicionRepository.flush()
            }
        }

        // Asociar los jugadores con la formación
        formacion.jugadores.forEach { jugadorEnPosicion ->
            jugadorEnPosicion.formacion = formacion
        }
        return formacionRepository.save(formacion)
    }

    fun delete(id: Int) {
        if (!formacionRepository.existsById(id)) {
            throw NoSuchElementException("Formación no encontrada con id: $id")
        }
        formacionRepository.deleteById(id)
    }

    fun getLatestFormacion(): Formacion? {
        return formacionRepository.findFirstByOrderByIdFormacionDesc()
    }

    fun existsFormacion(): Boolean {
        return formacionRepository.count() > 0
    }
}
