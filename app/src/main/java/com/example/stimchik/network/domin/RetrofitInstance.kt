package com.example.stimchik

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServise{
    // Поиск игр по названию
    @GET("/api/1.0/games")
    suspend fun searchGames(
        @Query("title") title: String,
        @Query("limit") limit: Int = 30
    ): List<CheapSharkGame>

    @GET("/api/1.0/games")
    suspend fun getGameDetails(
        @Query("id") id: String
    ): GameDetails


}


object RetrofitInstance{
    private const val BASE_URL = "https://www.cheapshark.com"
    val api: ApiServise by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServise::class.java)
    }
}