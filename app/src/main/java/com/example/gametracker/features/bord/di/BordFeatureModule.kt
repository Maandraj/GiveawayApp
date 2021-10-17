package com.example.gametracker.features.bord.di

import com.example.gametracker.features.bord.data.api.GamerPowerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class BordFeatureModule {
    @Provides
    fun provideGamePowerApi(retrofit: Retrofit) =
        retrofit.create(GamerPowerApi::class.java)
}