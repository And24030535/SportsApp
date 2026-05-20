package com.example.sportsapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityFavoritesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesActivity : AppCompatActivity() {

    private val db   = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun favCol() = db.collection("users")
        .document(auth.currentUser?.uid ?: "guest")
        .collection("favorites")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val container = binding.favContainer

        favCol().get().addOnSuccessListener { snap ->
            if (snap.isEmpty) {
                val tv = TextView(this)
                tv.text     = "No tienes favoritos todavía."
                tv.textSize = 16f
                container.addView(tv)
            } else {
                snap.documents.forEach { doc ->
                    val title       = doc.getString("title") ?: doc.id
                    val description = doc.getString("description") ?: ""
                    val score       = doc.getString("score") ?: ""

                    val tv = TextView(this)
                    tv.text     = title
                    tv.textSize = 18f
                    tv.setPadding(0, 12, 0, 12)
                    tv.setOnClickListener {
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("description", description)
                        intent.putExtra("score", score)
                        startActivity(intent)
                    }
                    container.addView(tv)
                }
            }
        }.addOnFailureListener {
            val tv = TextView(this)
            tv.text = "Error al cargar favoritos."
            container.addView(tv)
        }
    }
}