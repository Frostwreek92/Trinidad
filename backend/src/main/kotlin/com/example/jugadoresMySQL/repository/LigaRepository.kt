package com.example.jugadoresMySQL.repository

import com.example.jugadoresMySQL.model.Liga
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LigaRepository : JpaRepository<Liga, Int>
