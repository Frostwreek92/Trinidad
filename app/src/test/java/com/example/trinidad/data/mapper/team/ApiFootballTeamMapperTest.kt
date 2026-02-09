package com.example.trinidad.data.mapper.team

import com.example.trinidad.data.remote.dto.apifootball.TeamDto
import com.example.trinidad.data.remote.dto.apifootball.TeamResponseItemDto
import com.example.trinidad.data.remote.dto.apifootball.VenueDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiFootballTeamMapperTest {

    @Test
    fun `map maps team correctly when all fields are present`() {
        val dto = TeamResponseItemDto(
            team = TeamDto(
                id = 1,
                name = "FC Barcelona",
                logo = "logo_url"
            ),
            venue = VenueDto(
                name = "Camp Nou",
                city = "Barcelona",
                capacity = 99354,
                id = null,
                address = "",
                surface = "",
                image = ""
            )
        )

        val result = ApiFootballTeamMapper.map(dto)

        assertThat(result.id).isEqualTo(1)
        assertThat(result.name).isEqualTo("FC Barcelona")
        assertThat(result.logo).isEqualTo("logo_url")
    }

    @Test
    fun `mapDetail maps detail correctly when venue is present`() {
        val dto = TeamResponseItemDto(
            team = TeamDto(
                id = 2,
                name = "Real Madrid",
                logo = "crest"
            ),
            venue = VenueDto(
                name = "Santiago Bernabeu",
                city = "Madrid",
                capacity = 81044,
                id = null,
                address = "",
                surface = "",
                image = ""
            )
        )

        val result = ApiFootballTeamMapper.mapDetail(dto)

        assertThat(result.id).isEqualTo(2)
        assertThat(result.name).isEqualTo("Real Madrid")
        assertThat(result.logo).isEqualTo("crest")
        assertThat(result.stadium).isEqualTo("Santiago Bernabeu")
        assertThat(result.city).isEqualTo("Madrid")
        assertThat(result.capacity).isEqualTo(81044)
    }

    @Test
    fun `mapDetail uses default values when venue is null`() {
        val dto = TeamResponseItemDto(
            team = TeamDto(
                id = 3,
                name = "Valencia CF",
                logo = null
            ),
            venue = null
        )

        val result = ApiFootballTeamMapper.mapDetail(dto)

        assertThat(result.logo).isEqualTo("")
        assertThat(result.stadium).isEqualTo("")
        assertThat(result.city).isEqualTo("")
        assertThat(result.capacity).isEqualTo(0)
    }
}
