package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.Equipo
import com.example.jugadoresMySQL.repository.EquipoRepository
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

@Service
class EquipoService(
    private val equipoRepository: EquipoRepository
) {

    fun findAll(): List<Equipo> = equipoRepository.findAll()

    fun findById(id: Int): Equipo =
        equipoRepository.findById(id).orElseThrow { NoSuchElementException("Equipo con id $id no encontrado") }

    fun save(equipo: Equipo): Equipo = equipoRepository.save(equipo)

    fun delete(id: Int) {
        if (!equipoRepository.existsById(id)) {
            throw NoSuchElementException("Equipo con id $id no encontrado")
        }
        equipoRepository.deleteById(id)
    }

    fun importarDesdeArchivoFijo(): Int {
        val archivo = File("data/equipos_ejemplo.csv")
        
        if (!archivo.exists()) {
            throw IllegalArgumentException("El archivo data/equipos_ejemplo.csv no existe")
        }

        val reader = BufferedReader(FileReader(archivo))

        val equipos = reader.lineSequence()
            .drop(1) // saltar cabecera
            .filter { it.isNotBlank() }
            .map { linea ->
                val partes = linea.split(',')
                    .map { it.trim() }

                if (partes.size < 2) {
                    throw IllegalArgumentException("Línea CSV no válida: '$linea'")
                }

                Equipo(
                    nombreEquipo = partes[0],
                    paisEquipo = partes[1]
                )
            }
            .toList()

        equipoRepository.saveAll(equipos)
        return equipos.size
    }
}
