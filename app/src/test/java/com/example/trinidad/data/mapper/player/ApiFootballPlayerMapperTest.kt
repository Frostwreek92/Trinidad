package com.example.trinidad.data.mapper.player

import com.example.trinidad.data.remote.dto.apifootball.GamesDto
import com.example.trinidad.data.remote.dto.apifootball.PlayerResponseItemDto
import com.example.trinidad.data.remote.dto.apifootball.PlayerDto
import com.example.trinidad.data.remote.dto.apifootball.PlayerStatisticsDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiFootballPlayerMapperTest {

    @Test
    fun `fromListItem maps player correctly when all fields are present`() {
        val dto = PlayerResponseItemDto(
            player = PlayerDto(
                id = 10,
                name = "Messi",
                photo = "photo_url",
                height = "170 cm",
                weight = "72 kg",
                nationality = "Argentina",
                age = 36,
                firstname = "",
                lastname = ""
            ),
            statistics = listOf(
                PlayerStatisticsDto(
                    games = GamesDto(
                        position = "Forward"
                    )
                )
            )
        )

        val result = ApiFootballPlayerMapper.fromListItem(dto)

        assertThat(result.id).isEqualTo(10)
        assertThat(result.name).isEqualTo("Messi")
        assertThat(result.position).isEqualTo("Forward")
        assertThat(result.photo).isEqualTo("photo_url")
        assertThat(result.height).isEqualTo("170 cm")
        assertThat(result.weight).isEqualTo("72 kg")
        assertThat(result.nationality).isEqualTo("Argentina")
    }

    @Test
    fun `fromListItem uses default values when optional fields are null`() {
        val dto = PlayerResponseItemDto(
            player = PlayerDto(
                id = 99,
                name = null,
                photo = null,
                height = null,
                weight = null,
                nationality = null,
                age = null,
                firstname = null,
                lastname = null
            ),
            statistics = emptyList()
        )

        val result = ApiFootballPlayerMapper.fromListItem(dto)

        assertThat(result.name).isEqualTo("Unknown")
        assertThat(result.position).isEqualTo("N/A")
        assertThat(result.photo).isEqualTo("")
        assertThat(result.height).isEqualTo("")
        assertThat(result.weight).isEqualTo("")
        assertThat(result.nationality).isEqualTo("")
    }

    @Test
    fun `toDetail maps detail correctly`() {
        val dto = PlayerResponseItemDto(
            player = PlayerDto(
                id = 7,
                name = "Cristiano",
                photo = "photo",
                height = "187 cm",
                weight = "83 kg",
                nationality = "Portugal",
                age = 39,
                firstname = "",
                lastname = ""
            ),
            statistics = listOf(
                PlayerStatisticsDto(
                    games = GamesDto(
                        position = "Forward"
                    )
                )
            )
        )

        val result = ApiFootballPlayerMapper.toDetail(dto)

        assertThat(result.id).isEqualTo(7)
        assertThat(result.name).isEqualTo("Cristiano")
        assertThat(result.position).isEqualTo("Forward")
        assertThat(result.age).isEqualTo(39)
        assertThat(result.nationality).isEqualTo("Portugal")
    }
}
