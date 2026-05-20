package com.example.sportsapp

import android.content.Intent
import com.example.sportsapp.adapters.ListItem
import com.example.sportsapp.ui.viewmodels.UiState

class TeamsActivity : BaseListActivity() {

    override fun getSectionTitle() = "Equipos"

    override fun loadData() {
        showLoading()
        viewModel.fetchTeams()

        viewModel.teamsState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    showSuccess()
                    val items = state.data.map { team ->
                        ListItem(team.strTeam, team.strLeague ?: "Liga desconocida")
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

