package com.example.trinidad.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trinidad.ui.components.AppTopBar
import com.example.trinidad.ui.screens.equipoLegendario.EquipoLegendarioScreen
import com.example.trinidad.ui.screens.home.HomeScreen
import com.example.trinidad.ui.screens.ligas.EquipoDetailScreen
import com.example.trinidad.ui.screens.ligas.JugadorDetailScreen
import com.example.trinidad.ui.screens.ligas.LigasEquiposJugadoresScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    val currentRoute =
        navController.currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            AppTopBar(
                showBackButton = currentRoute != Routes.Home.route,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(
                    onNavigateToLigas = {
                        navController.navigate(Routes.Ligas.route)
                    },
                    onNavigateToEquipoLegendario = {
                        navController.navigate(Routes.EquipoLegendario.route)
                    }
                )
            }

            composable(Routes.Ligas.route) {
                LigasEquiposJugadoresScreen(
                    onEquipoClick = { equipoId ->
                        navController.navigate(
                            Routes.EquipoDetail.createRoute(equipoId)
                        )
                    }
                )
            }

            composable(
                route = Routes.EquipoDetail.route,
                arguments = listOf(navArgument("equipoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val equipoId = backStackEntry.arguments?.getInt("equipoId") ?: return@composable

                EquipoDetailScreen(
                    equipoId = equipoId,
                    onJugadorClick = { jugadorId ->
                        navController.navigate(
                            Routes.JugadorDetail.createRoute(jugadorId)
                        )
                    }
                )
            }

            composable(
                route = Routes.JugadorDetail.route,
                arguments = listOf(navArgument("jugadorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jugadorId = backStackEntry.arguments?.getInt("jugadorId") ?: return@composable
                JugadorDetailScreen(jugadorId = jugadorId)
            }

            composable(
                route = "jugador/{jugadorId}",
                arguments = listOf(navArgument("jugadorId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jugadorId =
                    backStackEntry.arguments?.getInt("jugadorId") ?: return@composable

                JugadorDetailScreen(jugadorId)
            }


            composable(Routes.EquipoLegendario.route) {
                EquipoLegendarioScreen()
            }
        }
    }
}