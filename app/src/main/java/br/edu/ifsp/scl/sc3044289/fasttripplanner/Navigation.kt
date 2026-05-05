package br.edu.ifsp.scl.sc3044289.fasttripplanner

import android.content.Intent

object TripIntentExtras {
    const val DESTINATION = "br.edu.ifsp.scl.sc3044289.fasttripplanner.DESTINATION"
    const val DAYS = "br.edu.ifsp.scl.sc3044289.fasttripplanner.DAYS"
    const val DAILY_BUDGET = "br.edu.ifsp.scl.sc3044289.fasttripplanner.DAILY_BUDGET"
    const val ACCOMMODATION = "br.edu.ifsp.scl.sc3044289.fasttripplanner.ACCOMMODATION"
    const val HAS_TRANSPORT = "br.edu.ifsp.scl.sc3044289.fasttripplanner.HAS_TRANSPORT"
    const val HAS_FOOD = "br.edu.ifsp.scl.sc3044289.fasttripplanner.HAS_FOOD"
    const val HAS_TOURS = "br.edu.ifsp.scl.sc3044289.fasttripplanner.HAS_TOURS"
}

fun Intent.putTripData(viewModel: TripViewModel): Intent = apply {
    putExtra(TripIntentExtras.DESTINATION, viewModel.destination)
    putExtra(TripIntentExtras.DAYS, viewModel.days)
    putExtra(TripIntentExtras.DAILY_BUDGET, viewModel.dailyBudget)
    putExtra(TripIntentExtras.ACCOMMODATION, viewModel.accommodation)
    putExtra(TripIntentExtras.HAS_TRANSPORT, viewModel.hasTransport)
    putExtra(TripIntentExtras.HAS_FOOD, viewModel.hasFood)
    putExtra(TripIntentExtras.HAS_TOURS, viewModel.hasTours)
}

fun TripViewModel.loadFromIntent(intent: Intent) {
    destination = intent.getStringExtra(TripIntentExtras.DESTINATION).orEmpty()
    days = intent.getStringExtra(TripIntentExtras.DAYS).orEmpty()
    dailyBudget = intent.getStringExtra(TripIntentExtras.DAILY_BUDGET).orEmpty()
    accommodation = intent.getStringExtra(TripIntentExtras.ACCOMMODATION) ?: "Econômica"
    hasTransport = intent.getBooleanExtra(TripIntentExtras.HAS_TRANSPORT, false)
    hasFood = intent.getBooleanExtra(TripIntentExtras.HAS_FOOD, false)
    hasTours = intent.getBooleanExtra(TripIntentExtras.HAS_TOURS, false)
}
