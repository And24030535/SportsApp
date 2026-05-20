package com.example.sportsapp.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val NEWS_API_BASE_URL = "https://newsapi.org/v2/"
    private const val SPORTS_API_BASE_URL = "https://www.thesportsdb.com/api/v1/json/123/"

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val newsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val sportsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SPORTS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val newsApiService: NewsApiService by lazy {
        newsRetrofit.create(NewsApiService::class.java)
    }

    val sportsApiService: SportsApiService by lazy {
        sportsRetrofit.create(SportsApiService::class.java)
    }
}

