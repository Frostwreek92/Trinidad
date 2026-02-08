package com.example.trinidad.data.remote.dto.footballdata

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class CompetitionsResponseDtoTest {

    private val gson = Gson()

    @Test
    fun `competitions response dto is parsed correctly`() {
        val json = """
            {
              "count": 1,
              "competitions": [
                {
                  "id": 2014,
                  "name": "La Liga",
                  "emblem": "logo",
                  "area": {
                    "name": "Spain"
                  }
                }
              ]
            }
        """

        val dto = gson.fromJson(json, CompetitionsResponseDto::class.java)

        assertThat(dto.count).isEqualTo(1)
        assertThat(dto.competitions).hasSize(1)
        assertThat(dto.competitions.first().name).isEqualTo("La Liga")
    }
}
