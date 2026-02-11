package com.example.jugadoresMySQL

import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JugadoresMySqlApplication {
	@Bean
	fun init(jugadorService: JugadorService) = CommandLineRunner {
		// Initialize database with sample data if it's empty
		try {
			val jugadores = jugadorService.findAll()
			if (jugadores.isEmpty()) {
				println("No players found in database. Importing sample data...")
				val count = jugadorService.importarDesdeArchivoFijo()
				println("Successfully imported $count players")
			} else {
				println("Database already contains ${jugadores.size} players")
			}
		} catch (e: Exception) {
			println("Error initializing database: ${e.message}")
		}
	}
}

fun main(args: Array<String>) {
	runApplication<JugadoresMySqlApplication>(*args)
}
