package com.example.trinidad.data.remote

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ApiTypeTest {

    @Test
    fun `api type enum contains expected values`() {
        val values = ApiType.values().toList()

        assertThat(values).contains(ApiType.API_FOOTBALL)
        assertThat(values).contains(ApiType.FOOTBALL_DATA)
    }
}
