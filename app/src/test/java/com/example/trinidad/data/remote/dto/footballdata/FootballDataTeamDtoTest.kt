package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class FootballDataTeamDtoTest {

    private val gson = Gson()

    @Test
    fun `team dto is parsed correctly from json`() {
        val json = """
            {
              "id": 64,
              "name": "Liverpool",
              "shortName": "LIV",
              "crest": "crest_url",
              "venue": "Anfield",
              "area": {
                "name": "England"
              }
            }
        """

        val dto = gson.fromJson(json, TeamDto::class.java)

        assertThat(dto.name).isEqualTo("Liverpool")
        assertThat(dto.venue).isEqualTo("Anfield")
        assertThat(dto.area?.name).isEqualTo("England")
    }
}
