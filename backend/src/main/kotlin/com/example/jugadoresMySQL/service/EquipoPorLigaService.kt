package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.EquipoPorLiga
import com.example.jugadoresMySQL.repository.EquipoPorLigaRepository
import org.springframework.stereotype.Service

@Service
class EquipoPorLigaService(
    private val equipoPorLigaRepository: EquipoPorLigaRepository
) {
    
    fun findAll(): List<EquipoPorLiga> {
        return equipoPorLigaRepository.findAll()
    }
    
    fun findByLiga(idLiga: Int): List<EquipoPorLiga> {
        return equipoPorLigaRepository.findByLiga(idLiga)
    }
    
    fun findByEquipo(idEquipo: Int): List<EquipoPorLiga> {
        return equipoPorLigaRepository.findByEquipo(idEquipo)
    }
    
    fun save(equipoPorLiga: EquipoPorLiga): EquipoPorLiga {
        return equipoPorLigaRepository.save(equipoPorLiga)
    }
    
    fun deleteById(id: Int) {
        equipoPorLigaRepository.deleteById(id)
    }
}
