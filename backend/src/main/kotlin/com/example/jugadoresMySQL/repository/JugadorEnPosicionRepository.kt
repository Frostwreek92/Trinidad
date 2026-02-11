package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.JugadorEnPosicion
import com.example.jugadoresMySQL.model.Formacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JugadorEnPosicionRepository : JpaRepository<JugadorEnPosicion, Int> {
    fun deleteByFormacion(formacion: Formacion)
    fun findByFormacion(formacion: Formacion): List<JugadorEnPosicion>
}
