package com.example.gametracker.features.bord.data

import com.example.gametracker.features.bord.data.api.GamerPowerApi
import com.example.gametracker.features.bord.data.api.model.mapper.GiveawayMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GiveawayRepo @Inject constructor(
    private val giveawayMapper: GiveawayMapper,
    private val api: GamerPowerApi
) {
    suspend fun getAllGiveaway() = withContext(Dispatchers.IO) {
        api.getAllGiveaways().let { res -> res.map { giveawayMapper.map(it) } }
    }

    suspend fun getFilterGiveaway(platforms: String, types: String, sorting: String) =
        withContext(Dispatchers.IO) {
            api.getFilterGiveaways(
                platforms = platforms,
                types = types,
                sorting = sorting
            ).let { res -> res.map { giveawayMapper.map(it) } }
        }
}