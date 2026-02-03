package com.example.jugadoresMySQL

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JugadorRepository : JpaRepository<Jugador, Int>
