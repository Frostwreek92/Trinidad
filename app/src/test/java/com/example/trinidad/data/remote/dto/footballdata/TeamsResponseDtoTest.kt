package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class TeamsResponseDtoTest {

    private val gson = Gson()

    @Test
    fun `teams response dto is parsed correctly`() {
        val json = """
            {
              "teams": [
                {
                  "id": 81,
                  "name": "Barcelona",
                  "crest": "crest"
                }
              ]
            }
        """

        val dto = gson.fromJson(json, TeamsResponseDto::class.java)

        assertThat(dto.teams).hasSize(1)
        assertThat(dto.teams.first().name).isEqualTo("Barcelona")
    }
}
