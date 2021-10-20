package com.example.gametracker.features.bord.utils

import com.example.gametracker.R

object PlatformLogo {
    fun getLogo(name : String) : Int {
       return when(name){
            "Steam" -> R.drawable.ic_steam
            "Ubisoft" -> R.drawable.ic_ubisoft
            "Pc" -> R.drawable.ic_windows
            "Gog" -> R.drawable.ic_gog_com
            "Ps4" -> R.drawable.ic_sony_playstation
            "Ps5" -> R.drawable.ic_sony_playstation
            "Xbox360" -> R.drawable.ic_xbox
            "XboxOne" -> R.drawable.ic_xbox
            "XboxXS" -> R.drawable.ic_xbox
            "Switch" -> R.drawable.ic_nintendo_switch
            "Android" -> R.drawable.ic_android
            "Ios" -> R.drawable.ic_ios
            "Vr" -> R.drawable.ic_vr
            "Battlenet" -> R.drawable.ic_battlenet
            "Origin" -> R.drawable.ic_origin
            "Epic games" -> R.drawable.ic_epic_games
           else -> R.drawable.ic_circle
       }
    }
}

