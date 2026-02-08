package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.footballdata.PlayerDto
import com.example.trinidad.data.remote.dto.footballdata.SquadPlayerDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FootballDataPlayerMapperTest {

    @Test
    fun `fromSquadItem maps player correctly`() {
        val dto = PlayerDto(
            id = 15,
            name = "Pedri",
            position = "Midfielder"
        )

        val result = FootballDataPlayerMapper.fromSquadItem(dto)

        assertThat(result.id).isEqualTo(15)
        assertThat(result.name).isEqualTo("Pedri")
        assertThat(result.position).isEqualTo("Midfielder")
        assertThat(result.photo).isEqualTo("")
        assertThat(result.height).isEqualTo("N/A")
        assertThat(result.weight).isEqualTo("N/A")
        assertThat(result.nationality).isEqualTo("N/A")
    }

    @Test
    fun `fromSquadItem uses default position when null`() {
        val dto = PlayerDto(
            id = 20,
            name = "Gavi",
            position = null
        )

        val result = FootballDataPlayerMapper.fromSquadItem(dto)

        assertThat(result.position).isEqualTo("N/A")
    }

    @Test
    fun `toDetail maps squad player detail correctly`() {
        val dto = SquadPlayerDto(
            id = 8,
            name = "Iniesta",
            position = "Midfielder",
            dateOfBirth = "",
            nationality = ""
        )

        val result = FootballDataPlayerMapper.toDetail(dto)

        assertThat(result.id).isEqualTo(8)
        assertThat(result.name).isEqualTo("Iniesta")
        assertThat(result.position).isEqualTo("Midfielder")
        assertThat(result.age).isEqualTo(0)
        assertThat(result.nationality).isEqualTo("N/A")
    }
}
