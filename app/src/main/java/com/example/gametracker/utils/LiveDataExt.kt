package com.example.gametracker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.asLiveData(): LiveData<T> = this
