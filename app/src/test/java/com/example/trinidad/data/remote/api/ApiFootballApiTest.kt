package com.example.trinidad.data.remote.api

import com.example.trinidad.data.remote.dto.apifootball.LeaguesResponseDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFootballApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiFootballApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getLeagues sends correct request and parses response`() = runTest {
        val responseBody = """
            {
              "response": []
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        val result: LeaguesResponseDto = api.getLeagues()
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/leagues")
        assertThat(result.response).isEmpty()
    }

    @Test
    fun `getTeamsByLeague sends correct query params`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "response": [] }""")
        )

        api.getTeamsByLeague(leagueId = 39, season = 2023)
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/teams?league=39&season=2023")
    }

    @Test
    fun `getPlayersByTeam uses default season when not provided`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "response": [] }""")
        )

        api.getPlayersByTeam(teamId = 100)
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/players?team=100&season=2023")
    }
}
