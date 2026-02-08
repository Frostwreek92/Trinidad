package com.example.trinidad.data.remote.dto.apifootball

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class VenueDtoTest {

    private val gson = Gson()

    @Test
    fun `venue dto is parsed correctly from json`() {
        val json = """
            {
              "id": 10,
              "name": "Old Trafford",
              "address": "Sir Matt Busby Way",
              "city": "Manchester",
              "capacity": 74879,
              "surface": "Grass",
              "image": "image_url"
            }
        """

        val dto = gson.fromJson(json, VenueDto::class.java)

        assertThat(dto.name).isEqualTo("Old Trafford")
        assertThat(dto.city).isEqualTo("Manchester")
        assertThat(dto.capacity).isEqualTo(74879)
    }
}
