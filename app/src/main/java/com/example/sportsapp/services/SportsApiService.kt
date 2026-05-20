package com.example.sportsapp.services

import com.example.sportsapp.models.EventsResponse
import com.example.sportsapp.models.LiveEventsResponse
import com.example.sportsapp.models.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApiService {

    @GET("searchteams.php")
    suspend fun getTeamsByLeague(
        @Query("t") teamName: String
    ): TeamsResponse

    @GET("eventspastleague.php")
    suspend fun getEventsByLeague(
        @Query("id") idLeague: String,
        @Query("s") season: String = "2025-2026"
    ): EventsResponse

    @GET("eventsnextleague.php")
    suspend fun getLiveEvents(
        @Query("id") idLeague: String
    ): LiveEventsResponse

    @GET("lookupteam.php")
    suspend fun getTeamById(
        @Query("id") idTeam: String
    ): TeamsResponse
}