package com.example.sportsapp

import android.content.Intent
import com.example.sportsapp.adapters.ListItem
import com.example.sportsapp.ui.viewmodels.UiState

class LiveEventsActivity : BaseListActivity() {

    override fun getSectionTitle() = "Eventos en Vivo"

    override fun loadData() {
        showLoading()
        viewModel.fetchLiveEvents("4350") // Premier League ID

        viewModel.liveEventsState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    showSuccess()
                    val items = state.data.map { event ->
                        val marcador = "${event.intHomeScore ?: 0} - ${event.intAwayScore ?: 0}"
                        ListItem("${event.strHomeTeam} vs ${event.strAwayTeam}", "Marcador: $marcador")
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

