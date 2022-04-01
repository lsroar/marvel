package com.lsroar.data.repository

import com.lsroar.data.remote.entity.CharacterDataWrapperDAO

interface MarvelCharacterListRepository {
    suspend fun characterList(): CharacterDataWrapperDAO
}
