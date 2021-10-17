package com.example.gametracker.features.bord.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gametracker.features.bord.domain.GiveawayInteractor
import com.example.gametracker.features.bord.domain.model.Giveaway
import com.example.gametracker.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiveawayViewModel @Inject constructor(
    private val giveawayInteractor: GiveawayInteractor
) : ViewModel() {


    private val _giveaways = MutableLiveData<List<Giveaway>>()
    val giveaways = _giveaways.asLiveData()

    fun getAllGiveaways() =
        viewModelScope.launch {
            giveawayInteractor.getAllGiveaway()
        }

    fun getFilterGiveaway(platforms: String?, types: String?, sorting: String?) =
        viewModelScope.launch {
        giveawayInteractor.getFilterGiveaway(
            platforms =  platforms ?: allPlatforms ,
            types = types ?: "game.loot.beta",
            sorting = sorting ?: "date.value.popularity" )
    }



    companion object{
        private val allPlatforms =
            "pc.steam.epic-games-store." +
                    "ubisoft.gog.itchio." +
                    "ps4.ps5.xbox-one.xbox-series-xs.switch." +
                    "android.ios.vr.battlenet.origin." +
                    "drm-free.xbox-360"
    }
}