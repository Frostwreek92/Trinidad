package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class SquadDtoTest {

    private val gson = Gson()

    @Test
    fun `squad response dto is parsed correctly`() {
        val json = """
            {
              "squad": [
                {
                  "id": 1,
                  "name": "Busquets",
                  "position": "Midfielder"
                }
              ]
            }
        """

        val dto = gson.fromJson(json, SquadResponseDto::class.java)

        assertThat(dto.squad).hasSize(1)
        assertThat(dto.squad.first().name).isEqualTo("Busquets")
    }

    @Test
    fun `squad player dto is deserialized correctly`() {
        val json = """
            {
              "id": 5,
              "name": "Pique",
              "position": "Defender",
              "dateOfBirth": "1987-02-02",
              "nationality": "Spain"
            }
        """

        val dto = gson.fromJson(json, SquadPlayerDto::class.java)

        assertThat(dto.name).isEqualTo("Pique")
        assertThat(dto.nationality).isEqualTo("Spain")
    }
}
