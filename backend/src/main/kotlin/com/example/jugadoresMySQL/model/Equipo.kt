package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "equipos")
data class Equipo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    var idEquipo: Int? = null,

    @Column(name = "nombre_equipo")
    var nombreEquipo: String = "",

    @Column(name = "pais_equipo")
    var paisEquipo: String = "",

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_liga", referencedColumnName = "id_liga")
    var liga: Liga? = null,

    @Column(name = "foto")
    var foto: String = ""
) {
    constructor() : this(null, "", "", null, "")
}
