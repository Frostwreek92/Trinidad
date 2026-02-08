package com.example.trinidad.data.remote.dto.apifootball

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class TeamDtoTest {

    private val gson = Gson()

    @Test
    fun `team response dto is deserialized correctly`() {
        val json = """
            {
              "response": [
                {
                  "team": {
                    "id": 529,
                    "name": "FC Barcelona",
                    "logo": "logo"
                  },
                  "venue": {
                    "id": 1,
                    "name": "Camp Nou",
                    "city": "Barcelona",
                    "capacity": 99354
                  }
                }
              ]
            }
        """

        val dto = gson.fromJson(json, TeamsResponseDto::class.java)

        val team = dto.response.first()

        assertThat(team.team.name).isEqualTo("FC Barcelona")
        assertThat(team.venue?.name).isEqualTo("Camp Nou")
        assertThat(team.venue?.capacity).isEqualTo(99354)
    }
}
