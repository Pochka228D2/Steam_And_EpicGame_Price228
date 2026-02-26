package com.example.stimchik

import com.google.gson.annotations.SerializedName

// Для списка игр (поиск)
data class CheapSharkGame(
    @SerializedName("gameID") val gameID: String,
    @SerializedName("steamAppID") val steamAppID: String?,
    @SerializedName("cheapest") val cheapest: String?,
    @SerializedName("cheapestDealID") val cheapestDealID: String?,
    @SerializedName("external") val external: String, // Название игры
    @SerializedName("thumb") val thumb: String
)

// Для детальной инфы об игре
data class GameDetails(
    @SerializedName("info") val info: Info,
    @SerializedName("deals") val deals: List<Deal>
)

data class Info(
    @SerializedName("title") val title: String,
    @SerializedName("thumb") val thumb: String
)

data class Deal(
    @SerializedName("storeID") val storeID: String,
    @SerializedName("dealID") val dealID: String,
    @SerializedName("price") val price: String,
    @SerializedName("retailPrice") val retailPrice: String,
    @SerializedName("savings") val savings: String
)