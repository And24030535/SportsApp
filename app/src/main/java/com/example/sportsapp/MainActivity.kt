package com.example.sportsapp

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import com.example.sportsapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  // <- mueve binding a propiedad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btnNews.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }
        binding.btnTeams.setOnClickListener {
            startActivity(Intent(this, TeamsActivity::class.java))
        }
        binding.btnMatches.setOnClickListener {
            startActivity(Intent(this, MatchesActivity::class.java))
        }
        binding.btnLive.setOnClickListener {
            startActivity(Intent(this, LiveEventsActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorites -> { startActivity(Intent(this, FavoritesActivity::class.java)); true }
            R.id.menu_login     -> { startActivity(Intent(this, LoginActivity::class.java)); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}