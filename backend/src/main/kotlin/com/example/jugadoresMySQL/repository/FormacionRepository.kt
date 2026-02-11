package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.Formacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormacionRepository : JpaRepository<Formacion, Int> {
    fun findFirstByOrderByIdFormacionDesc(): Formacion?
    fun existsByIdFormacion(id: Int): Boolean
}
