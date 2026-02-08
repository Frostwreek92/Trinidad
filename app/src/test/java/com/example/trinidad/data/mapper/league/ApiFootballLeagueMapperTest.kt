package com.example.trinidad.data.mapper.league

import com.example.trinidad.data.remote.dto.apifootball.CountryDto
import com.example.trinidad.data.remote.dto.apifootball.LeagueDto
import com.example.trinidad.data.remote.dto.apifootball.LeagueInfoDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiFootballLeagueMapperTest {

    @Test
    fun `map maps all fields correctly when optional fields are present`() {
        val dto = LeagueDto(
            league = LeagueInfoDto(
                id = 39,
                name = "Premier League",
                logo = "logo_url"
            ),
            country = CountryDto(
                name = "England"
            )
        )

        val result = ApiFootballLeagueMapper.map(dto)

        assertThat(result.id).isEqualTo(39)
        assertThat(result.name).isEqualTo("Premier League")
        assertThat(result.country).isEqualTo("England")
        assertThat(result.logo).isEqualTo("logo_url")
    }

    @Test
    fun `map sets empty strings when optional fields are null`() {
        val dto = LeagueDto(
            league = LeagueInfoDto(
                id = 140,
                name = "La Liga",
                logo = null
            ),
            country = CountryDto(
                name = null
            )
        )

        val result = ApiFootballLeagueMapper.map(dto)

        assertThat(result.id).isEqualTo(140)
        assertThat(result.name).isEqualTo("La Liga")
        assertThat(result.country).isEqualTo("")
        assertThat(result.logo).isEqualTo("")
    }
}
