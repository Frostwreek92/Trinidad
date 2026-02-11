package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.Liga
import com.example.jugadoresMySQL.repository.LigaRepository
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

@Service
class LigaService(
    private val ligaRepository: LigaRepository
) {

    fun findAll(): List<Liga> = ligaRepository.findAll()

    fun findById(id: Int): Liga =
        ligaRepository.findById(id).orElseThrow { NoSuchElementException("Liga con id $id no encontrada") }

    fun save(liga: Liga): Liga = ligaRepository.save(liga)

    fun delete(id: Int) {
        if (!ligaRepository.existsById(id)) {
            throw NoSuchElementException("Liga con id $id no encontrada")
        }
        ligaRepository.deleteById(id)
    }

    fun importarDesdeArchivoFijo(): Int {
        val archivo = File("data/ligas_ejemplo.csv")
        
        if (!archivo.exists()) {
            throw IllegalArgumentException("El archivo data/ligas_ejemplo.csv no existe")
        }

        val reader = BufferedReader(FileReader(archivo))

        val ligas = reader.lineSequence()
            .drop(1) // saltar cabecera
            .filter { it.isNotBlank() }
            .map { linea ->
                val partes = linea.split(',')
                    .map { it.trim() }

                if (partes.size < 2) {
                    throw IllegalArgumentException("Línea CSV no válida: '$linea'")
                }

                Liga(
                    nombreLiga = partes[0],
                    paisLiga = partes[1]
                )
            }
            .toList()

        ligaRepository.saveAll(ligas)
        return ligas.size
    }
}
