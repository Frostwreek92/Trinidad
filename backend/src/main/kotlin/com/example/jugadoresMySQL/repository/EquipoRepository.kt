package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.Equipo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipoRepository : JpaRepository<Equipo, Int>
