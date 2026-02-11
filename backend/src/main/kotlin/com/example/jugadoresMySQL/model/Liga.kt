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
    var paisLiga: String = "",

    @OneToMany(mappedBy = "liga", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var equipos: List<Equipo> = emptyList<Equipo>(),

    @Column(name = "foto")
    var foto: String = ""
) {
    constructor() : this(null, "", "", emptyList<Equipo>(), "")
}
