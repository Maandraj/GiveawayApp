package com.example.gametracker.features.bord.data.api

import com.example.gametracker.features.bord.data.api.model.GiveawayRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface GamerPowerApi {
    @GET("/giveaways")
    suspend fun getAllGiveaways(): List<GiveawayRes>

    @GET("/giveaways/")
    suspend fun getFilterGiveaways(
        @Query("platform") platforms: String,
        @Query("type") types: String,
        @Query("sort-by") sorting: String
    ): List<GiveawayRes>
}