package kinopoisk.lab.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kinopoisk.lab.domain.model.FilmType
import kinopoisk.lab.ui.details.DetailsScreenHolder
import kinopoisk.lab.ui.list.ListScreenHolder
import kinopoisk.lab.ui.theme.KinopoiskTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            KinopoiskTheme(darkTheme = false) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = FilmRoutes.LIST.name
                    ) {
                        composable(route = FilmRoutes.LIST.name) {
                            ListScreenHolder(navController = navController)
                        }

                        composable(
                            route = "${FilmRoutes.DETAILS.name}?filmId={filmId}&&filmType={filmType}",
                            arguments = listOf(navArgument("filmId") { defaultValue = 0 })
                        ) { backStackEntry ->
                            val filmId = backStackEntry.arguments?.getInt("filmId")!!.toLong()
                            val filmType =
                                FilmType.getByName(backStackEntry.arguments?.getString("filmType")!!)
                            DetailsScreenHolder(
                                filmId = filmId,
                                filmType = filmType,
                                onNavigateBack = navController::popBackStack
                            )
                        }
                    }
                }
            }
        }
    }
}