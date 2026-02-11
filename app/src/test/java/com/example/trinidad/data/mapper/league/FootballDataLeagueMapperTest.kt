package com.example.trinidad.data.mapper.league

import com.example.trinidad.data.remote.dto.footballdata.AreaDto
import com.example.trinidad.data.remote.dto.footballdata.CompetitionDto
import com.example.trinidad.domain.model.api.League
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FootballDataLeagueMapperTest {

    @Test
    fun `map maps all fields correctly when emblem is present`() {
        val dto = CompetitionDto(
            id = 2002,
            name = "Bundesliga",
            area = AreaDto(
                name = "Germany"
            ),
            emblem = "emblem_url"
        )

        val result: League = FootballDataLeagueMapper.map(dto)

        assertThat(result.id).isEqualTo(2002)
        assertThat(result.name).isEqualTo("Bundesliga")
        assertThat(result.country).isEqualTo("Germany")
        assertThat(result.logo).isEqualTo("emblem_url")
    }

    @Test
    fun `map sets empty logo when emblem is null`() {
        val dto = CompetitionDto(
            id = 2014,
            name = "La Liga",
            area = AreaDto(
                name = "Spain"
            ),
            emblem = null
        )

        val result = FootballDataLeagueMapper.map(dto)

        assertThat(result.logo).isEqualTo("")
    }
}
