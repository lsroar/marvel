package com.lsroar.data.repository

import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import io.reactivex.Single

interface MarvelCharacterDetailRepository {
    fun characterDetail(id: Int): Single<CharacterDataWrapperDAO>
}
