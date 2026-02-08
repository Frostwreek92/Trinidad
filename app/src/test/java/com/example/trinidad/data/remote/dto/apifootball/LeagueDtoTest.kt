package com.example.trinidad.data.remote.dto.apifootball

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class LeagueDtoTest {

    private val gson = Gson()

    @Test
    fun `league dto is correctly created`() {
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

        assertThat(dto.league.id).isEqualTo(39)
        assertThat(dto.league.name).isEqualTo("Premier League")
        assertThat(dto.country?.name).isEqualTo("England")
    }

    @Test
    fun `league dto is correctly deserialized from json`() {
        val json = """
            {
              "league": {
                "id": 140,
                "name": "La Liga",
                "logo": "logo"
              },
              "country": {
                "name": "Spain"
              }
            }
        """

        val dto = gson.fromJson(json, LeagueDto::class.java)

        assertThat(dto.league.name).isEqualTo("La Liga")
        assertThat(dto.country?.name).isEqualTo("Spain")
    }
}
