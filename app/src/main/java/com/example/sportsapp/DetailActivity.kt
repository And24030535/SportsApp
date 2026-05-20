package com.example.sportsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {

    private val db   = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun favCol() = db.collection("users")
        .document(auth.currentUser?.uid ?: "guest")
        .collection("favorites")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title       = intent.getStringExtra("title") ?: "Sin título"
        val description = intent.getStringExtra("description") ?: ""
        val score       = intent.getStringExtra("score") ?: ""

        binding.detailTitle.text       = title
        binding.detailDescription.text = description
        binding.detailScore.text       = score

        val btn    = binding.btnFavorite
        val docRef = favCol().document(title)

        // Verificar si ya es favorito
        docRef.get().addOnSuccessListener { snap ->
            btn.text = if (snap.exists()) "Eliminar de favoritos" else "Agregar a favoritos"
        }

        val user = FirebaseAuth.getInstance().currentUser
        android.util.Log.d("FAVORITOS", "Usuario: ${user?.uid}, anon: ${user?.isAnonymous}")

        btn.setOnClickListener {
            docRef.get().addOnSuccessListener { snap ->
                if (snap.exists()) {
                    docRef.delete()
                    btn.text = "Agregar a favoritos"
                } else {
                    val data = mapOf(
                        "title"       to title,
                        "description" to description,
                        "score"       to score
                    )
                    docRef.set(data)
                    btn.text = "Eliminar de favoritos"
                }
            }
        }
    }
}