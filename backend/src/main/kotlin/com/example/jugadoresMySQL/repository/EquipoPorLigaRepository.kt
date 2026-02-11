package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.EquipoPorLiga
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EquipoPorLigaRepository : JpaRepository<EquipoPorLiga, Int> {
    
    @Query("SELECT epl FROM EquipoPorLiga epl WHERE epl.liga.idLiga = :idLiga")
    fun findByLiga(@Param("idLiga") idLiga: Int): List<EquipoPorLiga>
    
    @Query("SELECT epl FROM EquipoPorLiga epl WHERE epl.equipo.idEquipo = :idEquipo")
    fun findByEquipo(@Param("idEquipo") idEquipo: Int): List<EquipoPorLiga>
}
