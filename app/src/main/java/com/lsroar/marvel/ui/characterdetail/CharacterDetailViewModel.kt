package com.lsroar.marvel.ui.characterdetail

import androidx.lifecycle.LiveData
import com.lsroar.marvel.ui.characterdetail.entity.CharacterDetailVO

interface CharacterDetailViewModel {
    fun characterDetail(): LiveData<CharacterDetailVO>
    fun isLoading(): LiveData<Boolean>
    fun error(): LiveData<Int>
    fun onExtraValue(id: Int)
    fun onExit()
    fun navigation(): LiveData<CharacterDetailNavigation>
}
