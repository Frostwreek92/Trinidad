package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.api.ApiFootballApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFootballNetworkModuleTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `api football client adds api key header`() = runTest {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "response": [] }""")
        )

        val okHttpClient = ApiFootballNetworkModule
            .javaClass
            .getDeclaredMethod("provideOkHttpClient")
            .apply { isAccessible = true }
            .invoke(ApiFootballNetworkModule) as OkHttpClient

        val api: ApiFootballApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballApi::class.java)

        // Act
        api.getLeagues()
        val request = mockWebServer.takeRequest()

        // Assert
        assertThat(request.getHeader("x-apisports-key")).isNotNull()
    }
}
