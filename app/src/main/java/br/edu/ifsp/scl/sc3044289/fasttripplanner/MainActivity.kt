package br.edu.ifsp.scl.sc3044289.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.theme.FastTripPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTripPlannerTheme {
                val navController = rememberNavController()
                
                NavHost(
                    navController = navController,
                    startDestination = Routes.TRAVEL_DATA
                ) {
                    // tela 1
                    composable(Routes.TRAVEL_DATA) {
                        TravelDataScreen(
                            viewModel = viewModel,
                            onNavigate = {
                                navController.navigate(Routes.TRAVEL_OPTIONS)
                            }
                        )
                    }

                    // tela 2
                    composable(Routes.TRAVEL_OPTIONS){
                        TravelOptionsScreen(
                            viewModel = viewModel,
                            onNavigate = {
                                navController.navigate(Routes.TRAVEL_SUMMARY)
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // tela 3
                    composable(Routes.TRAVEL_SUMMARY){
                        TravelSummaryScreen(
                            viewModel = viewModel,
                            onRestart = {
                                viewModel.reset()
                                navController.navigate(Routes.TRAVEL_DATA) {
                                    popUpTo(Routes.TRAVEL_DATA) { inclusive = true }
                                }
                            }
                        )
                    }
                }
                
            }
        }
    }
}