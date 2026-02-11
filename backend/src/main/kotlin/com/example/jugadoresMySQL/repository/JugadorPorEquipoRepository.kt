package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.JugadorPorEquipo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JugadorPorEquipoRepository : JpaRepository<JugadorPorEquipo, Int> {
    
    @Query("SELECT jpe FROM JugadorPorEquipo jpe WHERE jpe.equipo.idEquipo = :idEquipo")
    fun findByEquipo(@Param("idEquipo") idEquipo: Int): List<JugadorPorEquipo>
}
