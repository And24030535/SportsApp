package com.example.sportsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.models.Article
import com.example.sportsapp.models.Event
import com.example.sportsapp.models.LiveEvent
import com.example.sportsapp.models.Team
import com.example.sportsapp.services.RetrofitClient
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

sealed class UiState<T> {
    data class Loading<T>(val message: String = "Cargando...") : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val message: String) : UiState<T>()
}

class SportsViewModel : ViewModel() {

    private val newsApiKey = "564ce340c1c046849ddeef0c5fcd5ae0"
    private val sportsDbApiKey = "3" // TheSportsDB no requiere key

    // News
    private val _newsState = MutableLiveData<UiState<List<Article>>>()
    val newsState: LiveData<UiState<List<Article>>> = _newsState

    // Teams
    private val _teamsState = MutableLiveData<UiState<List<Team>>>()
    val teamsState: LiveData<UiState<List<Team>>> = _teamsState

    private val _eventsState = MutableLiveData<UiState<List<Event>>>()
    val eventsState: LiveData<UiState<List<Event>>> = _eventsState

    // Live Events
    private val _liveEventsState = MutableLiveData<UiState<List<LiveEvent>>>()
    val liveEventsState: LiveData<UiState<List<LiveEvent>>> = _liveEventsState


    //Favoritos
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private fun favCol() = db.collection("users")
        .document(auth.currentUser?.uid ?: "guest")
        .collection("favorites")

    fun addFavorite(key: String, data: Map<String, Any>) {
        favCol().document(key).set(data)
    }

    fun removeFavorite(key: String) {
        favCol().document(key).delete()
    }


    fun fetchNews(query: String = "Futbol 2026") {
        _newsState.value = UiState.Loading("Cargando noticias...")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.newsApiService.getLatestNews(
                    query = query,
                    apiKey = newsApiKey
                )

                if (response.articles.isNotEmpty()) {
                    _newsState.value = UiState.Success(response.articles)
                } else {
                    _newsState.value = UiState.Error("No se encontraron noticias")
                }
            } catch (e: Exception) {
                Log.e("SportsViewModel", "Error fetching news: ${e.message}", e)
                _newsState.value = UiState.Error("Error: ${e.message ?: "Error desconocido"}")
            }
        }
    }
    fun fetchTeams() {
        _teamsState.value = UiState.Loading("Cargando equipos...")
        viewModelScope.launch {
            val equipos = listOf(
                Team("1", "Club América", "Soccer", "Liga MX", "4350", "Estadio Azteca", "Club más popular de México", null),
                Team("2", "Chivas Guadalajara", "Soccer", "Liga MX", "4350", "Estadio Akron", "Único equipo 100% mexicano", null),
                Team("3", "Cruz Azul", "Soccer", "Liga MX", "4350", "Estadio Azteca", "La Máquina Cementera", null),
                Team("4", "Pumas UNAM", "Soccer", "Liga MX", "4350", "Estadio Olímpico Universitario", "equipo universitario de México", null),
                Team("5", "Tigres UANL", "Soccer", "Liga MX", "4350", "Estadio Universitario", "Club del norte de México", null),
                Team("6", "Monterrey", "Soccer", "Liga MX", "4350", "Estadio BBVA", "Los Rayados", null),
                Team("7", "Pachuca", "Soccer", "Liga MX", "4350", "Estadio Hidalgo", "Los Tuzos", null),
                Team("8", "Toluca", "Soccer", "Liga MX", "4350", "Estadio Nemesio Diez", "Los Diablos Rojos", null)
            )
            _teamsState.value = UiState.Success(equipos)
        }
    }
    fun fetchEvents(leagueId: String = "4350") {
        _eventsState.value = UiState.Loading("Cargando encuentros...")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.sportsApiService.getEventsByLeague(leagueId, "2025-2026")
                val events = response.results ?: emptyList()
                if (events.isNotEmpty()) {
                    _eventsState.value = UiState.Success(events)
                } else {
                    _eventsState.value = UiState.Error("No se encontraron encuentros")
                }
            } catch (e: Exception) {
                Log.e("SportsViewModel", "Error fetching events: ${e.message}", e)
                _eventsState.value = UiState.Error("Error: ${e.message ?: "Error desconocido"}")
            }
        }
    }

    fun fetchLiveEvents(leagueId: String = "4350") {
        _liveEventsState.value = UiState.Loading("Cargando eventos en vivo...")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.sportsApiService.getLiveEvents(leagueId)
                val events = response.results ?: emptyList()
                if (events.isNotEmpty()) {
                    _liveEventsState.value = UiState.Success(events)
                } else {
                    _liveEventsState.value = UiState.Error("No hay eventos en vivo")
                }
            } catch (e: Exception) {
                _liveEventsState.value = UiState.Error("Error: ${e.message ?: "Error desconocido"}")
            }
        }
    }
}


