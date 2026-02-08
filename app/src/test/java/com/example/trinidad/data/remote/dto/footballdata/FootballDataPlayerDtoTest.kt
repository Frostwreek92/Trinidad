package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class FootballDataPlayerDtoTest {

    private val gson = Gson()

    @Test
    fun `player dto holds correct values`() {
        val dto = PlayerDto(
            id = 10,
            name = "Pedri",
            position = "Midfielder"
        )

        assertThat(dto.id).isEqualTo(10)
        assertThat(dto.name).isEqualTo("Pedri")
        assertThat(dto.position).isEqualTo("Midfielder")
    }

    @Test
    fun `player dto is deserialized from json`() {
        val json = """
            {
              "id": 8,
              "name": "Iniesta",
              "position": "Midfielder"
            }
        """

        val dto = gson.fromJson(json, PlayerDto::class.java)

        assertThat(dto.name).isEqualTo("Iniesta")
        assertThat(dto.position).isEqualTo("Midfielder")
    }
}
