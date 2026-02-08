package com.example.trinidad.data.remote.dto.apifootball

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class PlayersResponseDtoTest {

    private val gson = Gson()

    @Test
    fun `players response dto is parsed correctly`() {
        val json = """
            {
              "response": [
                {
                  "player": {
                    "id": 1,
                    "name": "Pedri",
                    "firstname": "Pedro",
                    "lastname": "Gonzalez",
                    "age": 21,
                    "nationality": "Spain",
                    "height": "174 cm",
                    "weight": "60 kg",
                    "photo": "photo"
                  },
                  "statistics": [
                    {
                      "games": {
                        "position": "Midfielder"
                      }
                    }
                  ]
                }
              ]
            }
        """

        val dto = gson.fromJson(json, PlayersResponseDto::class.java)

        assertThat(dto.response).hasSize(1)
        assertThat(dto.response.first().player.name).isEqualTo("Pedri")
        assertThat(dto.response.first().statistics.first().games.position)
            .isEqualTo("Midfielder")
    }
}
