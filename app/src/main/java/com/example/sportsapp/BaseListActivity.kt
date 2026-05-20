package com.example.sportsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsapp.adapters.ListAdapter
import com.example.sportsapp.adapters.ListItem
import com.example.sportsapp.databinding.ActivityListBinding
import com.example.sportsapp.ui.viewmodels.SportsViewModel
import android.content.Intent
import android.view.Menu
import android.view.MenuItem

abstract class BaseListActivity : AppCompatActivity() {

    protected lateinit var binding: ActivityListBinding
    protected lateinit var viewModel: SportsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sectionTitle.text = getSectionTitle()

        // Inicializar ViewModel
        viewModel = ViewModelProvider(this).get(SportsViewModel::class.java)

        // Cargar datos
        loadData()
    }

    protected open fun getSectionTitle(): String = ""

    protected abstract fun loadData()

    protected fun setupRecyclerView(items: List<ListItem>) {
        val adapter = ListAdapter(items) { item ->
            onItemClick(item)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    protected fun showLoading() {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.recyclerView.visibility = android.view.View.GONE
        binding.errorMessage.visibility = android.view.View.GONE
    }

    protected fun hideLoading() {
        binding.progressBar.visibility = android.view.View.GONE
    }

    protected fun showError(message: String) {
        binding.errorMessage.visibility = android.view.View.VISIBLE
        binding.errorMessage.text = message
        binding.recyclerView.visibility = android.view.View.GONE
    }

    protected fun showSuccess() {
        binding.recyclerView.visibility = android.view.View.VISIBLE
        binding.errorMessage.visibility = android.view.View.GONE
    }

    abstract fun onItemClick(item: ListItem)
}




