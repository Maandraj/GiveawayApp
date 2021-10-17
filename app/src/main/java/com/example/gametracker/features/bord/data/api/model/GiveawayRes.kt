package com.example.gametracker.features.bord.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiveawayRes(
    @Json(name = "description")
    val description: String,
    @Json(name = "end_date")
    val endDate: String,
    @Json(name = "gamerpower_url")
    val gamerpowerUrl: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "instructions")
    val instructions: String,
    @Json(name = "open_giveaway")
    val openGiveaway: String,
    @Json(name = "open_giveaway_url")
    val openGiveawayUrl: String,
    @Json(name = "platforms")
    val platforms: String,
    @Json(name = "published_date")
    val publishedDate: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "users")
    val users: Int,
    @Json(name = "worth")
    val worth: String
)