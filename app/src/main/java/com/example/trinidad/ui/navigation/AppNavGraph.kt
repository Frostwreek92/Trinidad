package com.example.trinidad.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trinidad.ui.components.AppTopBar
import com.example.trinidad.ui.screens.EquipoLegendarioScreen
import com.example.trinidad.ui.screens.HomeScreen
import com.example.trinidad.ui.screens.LigasScreen

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Ligas : Routes("ligas")
    object EquipoLegendario : Routes("equipo_legendario")
}

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
                LigasScreen()
            }

            composable(Routes.EquipoLegendario.route) {
                EquipoLegendarioScreen()
            }
        }
    }
}