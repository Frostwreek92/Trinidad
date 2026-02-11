package com.example.jugadoresMySQL.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Transient
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn

@Entity
@Table(name = "jugadores")
data class Jugador(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    var idJugador: Int? = null,

    @Column(name = "nombre_jugador")
    var nombreJugador: String = "",

    @Column(name = "id_equipo", insertable = false, updatable = false)
    var idEquipo: Int? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    var equipo: Equipo? = null,

    @Column(name = "posicion")
    var posicion: String = "",

    @Column(name = "edad")
    var edad: Int = 0,

    @Column(name = "dorsal")
    var dorsal: Int = 0,

    @Column(name = "foto")
    var foto: String = "",

    @Transient
    var nombreEquipo: String? = null
) {
    // Constructor sin argumentos para JPA
    constructor() : this(null, "", null, null, "", 0, 0, "")
}
