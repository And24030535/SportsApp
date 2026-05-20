package com.example.sportsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsapp.databinding.ActivityDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast

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
            val user = FirebaseAuth.getInstance().currentUser

            // Si es anónimo o no hay sesión, pedir que inicie sesión
            if (user == null || user.isAnonymous) {
                Toast.makeText(this, "Inicia sesión para agregar favoritos", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }

            // Si está autenticado, guardar/eliminar favorito
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