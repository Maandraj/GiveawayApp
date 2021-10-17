package com.example.gametracker.features.bord.domain.model

data class Giveaway(
    val description: String,
    val endDate: String,
    val id: Int,
    val image: String,
    val instructions: String,
    val openGiveawayUrl: String,
    val platforms: String,
    val publishedDate: String,
    val status: String,
    val title: String,
    val type: String,
    val users: Int,
    val worth: String
)