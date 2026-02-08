package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class CompetitionDtoTest {

    private val gson = Gson()

    @Test
    fun `competition dto is created correctly`() {
        val dto = CompetitionDto(
            id = 2001,
            name = "Champions League",
            emblem = "emblem_url",
            area = AreaDto(
                name = "Europe"
            )
        )

        assertThat(dto.id).isEqualTo(2001)
        assertThat(dto.name).isEqualTo("Champions League")
        assertThat(dto.area.name).isEqualTo("Europe")
    }

    @Test
    fun `competition dto is deserialized from json`() {
        val json = """
            {
              "id": 2002,
              "name": "Bundesliga",
              "emblem": "logo",
              "area": {
                "name": "Germany"
              }
            }
        """

        val dto = gson.fromJson(json, CompetitionDto::class.java)

        assertThat(dto.name).isEqualTo("Bundesliga")
        assertThat(dto.area.name).isEqualTo("Germany")
    }
}
