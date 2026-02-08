package com.example.trinidad.data.remote

import com.example.trinidad.data.remote.network.ApiFootballNetworkModule
import com.example.trinidad.data.remote.network.FootballDataNetworkModule

object ApiProvider {

    // 🔁 CAMBIA SOLO ESTO PARA CAMBIAR DE API API_FOOTBALL,FOOTBALL_DATA
    var currentApi: ApiType = ApiType.FOOTBALL_DATA

    val apiFootball by lazy {
        ApiFootballNetworkModule.provideApi()
    }

    val footballData by lazy {
        FootballDataNetworkModule.provideApi()
    }
}
