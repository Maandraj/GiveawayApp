package com.example.gametracker.features.bord.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gametracker.features.bord.domain.GiveawayInteractor
import com.example.gametracker.features.bord.domain.model.Giveaway
import com.example.gametracker.features.bord.domain.model.Platform
import com.example.gametracker.features.bord.utils.PlatformSupport
import com.example.gametracker.utils.asLiveData
import com.squareup.moshi.JsonDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiveawayViewModel @Inject constructor(
    private val giveawayInteractor: GiveawayInteractor
) : ViewModel() {

    private val _platforms = MutableLiveData<List<Platform>>()
    val platforms = _platforms.asLiveData()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState.asLiveData()

    private val _lastException = MutableLiveData<Exception>()
    val exception = _lastException.asLiveData()

    init {
        viewModelScope.launch {
            _loadingState.postValue(false)
            getPlatfroms()


        }
    }

    private val _giveaways = MutableLiveData<List<Giveaway>>()
    val giveaways = _giveaways.asLiveData()

    fun getAllGiveaways() =
        viewModelScope.launch {
            _loadingState.postValue(true)

            _giveaways.postValue(giveawayInteractor.getAllGiveaway())
            _loadingState.postValue(false)
        }



    fun getFilterGiveaway(platforms: String?, types: String?, sorting: String?) =
        viewModelScope.launch {
            try {
                _loadingState.postValue(true)
                var sorted  = listOf<Giveaway>()
                val items =  giveawayInteractor.getFilterGiveaway(
                    platforms = platforms ?: allPlatforms,
                    types = types ?: ".game.loot.beta",
                    sorting = sorting ?: ".date")
                when {
                    sorting.equals(".popularity") -> sorted = items.sortedByDescending {  it.users }
                    sorting.equals(".date") -> sorted = items.sortedBy {  it.endDate }
                    sorting.equals(".value") -> sorted = items.sortedByDescending {  it.worth.replace("$", "").toFloatOrNull() }
                }
                _giveaways.postValue(sorted)
            }catch (ex : JsonDataException){
                    _lastException.postValue(ex)
            }
            _loadingState.postValue(false)

        }

    fun getPlatfroms(){
        val platformsArray = arrayListOf<Platform>()
        platformsArray.add(Platform("Pc", ".pc", 1))
        platformsArray.add(Platform("Steam", ".steam", 2))
        platformsArray.add(Platform("Epic games", ".epic-games-store", 3))
        platformsArray.add(Platform("Ubisoft", ".ubisoft", 3))
        platformsArray.add(Platform("Battlenet", ".battlenet", 3))
        platformsArray.add(Platform("Origin", ".origin", 3))
        platformsArray.add(Platform("Ps4", ".ps4", 5))
        platformsArray.add(Platform("Ps5", ".ps5", 5))
        platformsArray.add(Platform("Xbox360", ".xbox-360", 6))
        platformsArray.add(Platform("XboxOne", ".xbox-one", 6))
        platformsArray.add(Platform("XboxSX", ".xbox-series-xs", 6))
        platformsArray.add(Platform("Android", ".android", 7))
        platformsArray.add(Platform("Ios", ".ios", 7))
        platformsArray.add(Platform("Switch", ".switch", 8))
        platformsArray.add(Platform("Vr", ".vr", 9))
        platformsArray.add(Platform("Gog", ".gog", 12))
        platformsArray.add(Platform("Drm", ".drm-free", 12))
        platformsArray.add(Platform("Itchio", ".itchio", 12))
        val sortList = platformsArray.sortedWith(compareBy { it.popularity })
        _platforms.postValue(sortList)
    }

    companion object {
        private const val allPlatforms =
            "pc.steam.epic-games-store." +
                    "ubisoft.gog.itchio." +
                    "ps4.ps5.xbox-one.xbox-series-xs.switch." +
                    "android.ios.vr.battlenet.origin." +
                    "drm-free.xbox-360"
    }
}