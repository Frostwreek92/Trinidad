package com.example.trinidad.data.remote.dto.apifootball

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class PlayerDtoTest {

    private val gson = Gson()

    @Test
    fun `player dto holds correct data`() {
        val dto = PlayerDto(
            id = 10,
            name = "Messi",
            firstname = "Lionel",
            lastname = "Messi",
            age = 36,
            nationality = "Argentina",
            height = "170 cm",
            weight = "72 kg",
            photo = "photo_url"
        )

        assertThat(dto.name).isEqualTo("Messi")
        assertThat(dto.age).isEqualTo(36)
        assertThat(dto.nationality).isEqualTo("Argentina")
    }

    @Test
    fun `player dto is deserialized from json`() {
        val json = """
            {
              "id": 7,
              "name": "Cristiano Ronaldo",
              "firstname": "Cristiano",
              "lastname": "Ronaldo",
              "age": 39,
              "nationality": "Portugal",
              "height": "187 cm",
              "weight": "83 kg",
              "photo": "photo"
            }
        """

        val dto = gson.fromJson(json, PlayerDto::class.java)

        assertThat(dto.firstname).isEqualTo("Cristiano")
        assertThat(dto.lastname).isEqualTo("Ronaldo")
        assertThat(dto.age).isEqualTo(39)
    }
}
