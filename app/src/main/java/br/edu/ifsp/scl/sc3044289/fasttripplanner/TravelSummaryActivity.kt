package br.edu.ifsp.scl.sc3044289.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.screens.TravelSummaryScreen
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.theme.FastTripPlannerTheme

class TravelSummaryActivity : ComponentActivity() {
    private val viewModel: TripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadFromIntent(intent)
        }

        setContent {
            FastTripPlannerTheme {
                TravelSummaryScreen(
                    viewModel = viewModel,
                    onRestart = {
                        viewModel.reset()
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
