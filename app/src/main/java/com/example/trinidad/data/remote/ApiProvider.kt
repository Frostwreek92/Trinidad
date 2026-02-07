package com.example.trinidad.data.remote

import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.example.trinidad.data.remote.network.ApiFootballNetworkModule
import com.example.trinidad.data.remote.network.FootballDataNetworkModule

object ApiProvider {

    // 🔁 CAMBIA SOLO ESTO PARA CAMBIAR DE API
    var apiType: ApiType = ApiType.API_FOOTBALL

    val apiFootballApi: ApiFootballApi by lazy {
        ApiFootballNetworkModule.provideApi()
    }

    val footballDataApi: FootballDataApi by lazy {
        FootballDataNetworkModule.provideApi()
    }
}
