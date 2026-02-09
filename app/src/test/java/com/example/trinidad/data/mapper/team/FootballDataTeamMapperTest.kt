package com.example.trinidad.data.mapper.team

import com.example.trinidad.data.remote.dto.footballdata.AreaDto
import com.example.trinidad.data.remote.dto.footballdata.TeamDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FootballDataTeamMapperTest {

    @Test
    fun `map maps team correctly when fields are present`() {
        val dto = TeamDto(
            id = 100,
            name = "Bayern Munich",
            crest = "crest_url",
            venue = "Allianz Arena",
            area = AreaDto(
                name = "Germany"
            ),
            shortName = ""
        )

        val result = FootballDataTeamMapper.map(dto)

        assertThat(result.id).isEqualTo(100)
        assertThat(result.name).isEqualTo("Bayern Munich")
        assertThat(result.logo).isEqualTo("crest_url")
    }

    @Test
    fun `map uses default values when optional fields are null`() {
        val dto = TeamDto(
            id = 200,
            name = null,
            crest = null,
            venue = null,
            area = null,
            shortName = null
        )

        val result = FootballDataTeamMapper.map(dto)

        assertThat(result.name).isEqualTo("Desconocido")
        assertThat(result.logo).isEqualTo("")
    }

    @Test
    fun `mapDetail maps team detail correctly`() {
        val dto = TeamDto(
            id = 300,
            name = "PSG",
            crest = "logo",
            venue = "Parc des Princes",
            area = AreaDto(
                name = "France"
            ),
            shortName = ""
        )

        val result = FootballDataTeamMapper.mapDetail(dto)

        assertThat(result.id).isEqualTo(300)
        assertThat(result.name).isEqualTo("PSG")
        assertThat(result.logo).isEqualTo("logo")
        assertThat(result.stadium).isEqualTo("Parc des Princes")
        assertThat(result.city).isEqualTo("France")
        assertThat(result.capacity).isEqualTo(0)
    }
}
