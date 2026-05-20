package com.example.sportsapp

import android.content.Intent
import com.example.sportsapp.adapters.ListItem
import com.example.sportsapp.ui.viewmodels.UiState

class NewsActivity : BaseListActivity() {

    override fun getSectionTitle() = "Noticias Deportivas"

    override fun loadData() {
        showLoading()
        viewModel.fetchNews("futbol 2026")

        viewModel.newsState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    showSuccess()
                    val items = state.data.map { article ->
                        ListItem(article.title, article.description ?: "Sin descripción")
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

