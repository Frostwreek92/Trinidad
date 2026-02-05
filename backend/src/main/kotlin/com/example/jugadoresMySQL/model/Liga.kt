package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "ligas")
data class Liga(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_liga")
    var idLiga: Int? = null,

    @Column(name = "nombre_liga")
    var nombreLiga: String = "",

    @Column(name = "pais_liga")
    var paisLiga: String = ""
) {
    constructor() : this(null, "", "")
}
