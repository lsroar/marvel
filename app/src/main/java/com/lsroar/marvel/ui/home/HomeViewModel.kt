package com.lsroar.marvel.ui.home

import androidx.lifecycle.LiveData
import com.lsroar.marvel.ui.home.entity.CharacterVO

interface HomeViewModel {

    fun isLoading(): LiveData<Boolean>
    fun error(): LiveData<Int>
    fun characterList(): LiveData<List<CharacterVO>>
    fun onItemClick(id: Int)
    fun itemToLoad(): LiveData<Int>
    fun onScreenLoaded()
    fun onRetry()
}
