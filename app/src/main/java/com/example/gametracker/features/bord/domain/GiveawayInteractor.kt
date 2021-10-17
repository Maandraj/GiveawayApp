package com.example.gametracker.features.bord.domain

import com.example.gametracker.features.bord.data.GiveawayRepo
import com.example.gametracker.features.bord.domain.model.Giveaway
import com.example.gametracker.features.bord.utils.Platform
import javax.inject.Inject

class GiveawayInteractor @Inject constructor(
    private val giveawayRepo: GiveawayRepo
) {

    suspend fun getAllGiveaway() = giveawayRepo.getAllGiveaway()

    suspend fun getFilterGiveaway(platforms: String , types: String, sorting: String ) : List<Giveaway>{

        return giveawayRepo.getFilterGiveaway(platforms = platforms, types = types, sorting = sorting)
    }
}