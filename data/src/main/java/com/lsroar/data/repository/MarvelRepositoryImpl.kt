package com.lsroar.data.repository

import com.lsroar.data.BuildConfig
import com.lsroar.data.remote.MarvelApi
import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import io.reactivex.Single

class MarvelRepositoryImpl(
    private val marvelApi: MarvelApi
) : MarvelCharacterListRepository, MarvelCharacterDetailRepository {

    override suspend fun characterList(): CharacterDataWrapperDAO {
        return marvelApi.characters(
            BuildConfig.MARVEL_TS,
            BuildConfig.MARVEL_APIKEY,
            BuildConfig.MARVEL_HASH
        ).body()!!
    }

    override fun characterDetail(id: Int): Single<CharacterDataWrapperDAO> {
        return marvelApi.characterDetail(
            id,
            BuildConfig.MARVEL_TS,
            BuildConfig.MARVEL_APIKEY,
            BuildConfig.MARVEL_HASH
        ).map {
            return@map it.body()
        }
    }
}
