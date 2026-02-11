package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.Jugador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JugadorRepository : JpaRepository<Jugador, Int>
