package br.edu.ifsp.scl.sc3044289.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.screens.TravelOptionsScreen
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.theme.FastTripPlannerTheme

class TravelOptionsActivity : ComponentActivity() {
    private val viewModel: TripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadFromIntent(intent)
        }

        setContent {
            FastTripPlannerTheme {
                TravelOptionsScreen(
                    viewModel = viewModel,
                    onNavigate = {
                        val intent = Intent(this, TravelSummaryActivity::class.java)
                            .putTripData(viewModel)
                        startActivity(intent)
                    },
                    onBack = {
                        finish()
                    }
                )
            }
        }
    }
}
