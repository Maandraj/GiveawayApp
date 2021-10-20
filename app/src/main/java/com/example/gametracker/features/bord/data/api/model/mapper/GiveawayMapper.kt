package com.example.gametracker.features.bord.data.api.model.mapper

import com.example.gametracker.features.bord.data.api.model.GiveawayRes
import com.example.gametracker.features.bord.domain.model.Giveaway
import javax.inject.Inject

class GiveawayMapper @Inject constructor() {
    fun map(res: GiveawayRes) = Giveaway(
        id = res.id,
        title = res.title,
        description = res.description,
        endDate = res.endDate,
        image = res.image,
        instructions = res.instructions,
        openGiveawayUrl = res.openGiveawayUrl,
        platforms = res.platforms,
        status = res.status,
        type = res.type,
        users = res.users,
        worth = res.worth
    )
}