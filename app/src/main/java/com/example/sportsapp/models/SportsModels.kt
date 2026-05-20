package com.example.sportsapp.models

import com.google.gson.annotations.SerializedName

// TheSportsDB Response Models - Teams
data class TeamsResponse(
    @SerializedName("teams")
    val teams: List<Team>?
)

data class Team(
    @SerializedName("idTeam") val idTeam: String,
    @SerializedName("strTeam") val strTeam: String,
    @SerializedName("strSport") val strSport: String,
    @SerializedName("strLeague") val strLeague: String?,
    @SerializedName("idLeague") val idLeague: String?,
    @SerializedName("strStadium") val strStadium: String?,
    @SerializedName("strDescriptionEN") val strDescriptionEN: String?,
    @SerializedName("strTeamBadge") val strTeamBadge: String?
)

// TheSportsDB Response Models - Events/Matches
data class EventsResponse(
    @SerializedName("events")
    val results: List<Event>?
)

data class Event(
    @SerializedName("idEvent")
    val idEvent: String,
    @SerializedName("strEvent")
    val strEvent: String,
    @SerializedName("strLeague")
    val strLeague: String,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String,
    @SerializedName("intHomeScore")
    val intHomeScore: Int?,
    @SerializedName("intAwayScore")
    val intAwayScore: Int?,
    @SerializedName("strStatus")
    val strStatus: String?,
    @SerializedName("dateEvent")
    val dateEvent: String?,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String?
)

// TheSportsDB Response Models - Live Events
data class LiveEventsResponse(
    @SerializedName("events")
    val results: List<LiveEvent>?
)

data class LiveEvent(
    @SerializedName("idEvent")
    val idEvent: String,
    @SerializedName("strEvent")
    val strEvent: String,
    @SerializedName("strLeague")
    val strLeague: String,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String,
    @SerializedName("intHomeScore")
    val intHomeScore: Int?,
    @SerializedName("intAwayScore")
    val intAwayScore: Int?,
    @SerializedName("strStatus")
    val strStatus: String?,
    @SerializedName("dateEvent")
    val dateEvent: String?
)

