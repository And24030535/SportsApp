package com.example.sportsapp

import android.content.Intent
import com.example.sportsapp.adapters.ListItem
import com.example.sportsapp.ui.viewmodels.UiState

class MatchesActivity : BaseListActivity() {

    override fun getSectionTitle() = "Encuentros"

    override fun loadData() {
        showLoading()
        viewModel.fetchEvents("4350")

        viewModel.eventsState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    showSuccess()
                    val items = state.data.map { event ->
                        val marcador = "${event.intHomeScore ?: "-"} - ${event.intAwayScore ?: "-"}"
                        ListItem("${event.strHomeTeam} vs ${event.strAwayTeam}", marcador)
                    }
                    setupRecyclerView(items)
                }
                is UiState.Error -> {
                    hideLoading()
                    showError(state.message)
                }
            }
        }
    }

    override fun onItemClick(item: ListItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", item.title)
        intent.putExtra("description", item.subtitle)
        startActivity(intent)
    }
}

