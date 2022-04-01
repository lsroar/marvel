package com.lsroar.data

import com.lsroar.data.remote.MarvelApi
import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import com.lsroar.data.repository.MarvelCharacterListRepository
import com.lsroar.data.repository.MarvelRepositoryImpl
import io.reactivex.Single
import org.koin.dsl.module
import retrofit2.Response

val dataTestModule = module {

    single<MarvelApi> { MarvelApiTestMock() }
    single<MarvelCharacterListRepository> { MarvelRepositoryImpl(get()) }
}

class MarvelApiTestMock : MarvelApi {
    override suspend fun characters(
        ts: String,
        apikey: String,
        hash: String
    ): Response<CharacterDataWrapperDAO> {
        return Response.success(
            CharacterDataWrapperDAO( // any interesting object
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
    }

    override fun characterDetail(
        id: Int,
        ts: String,
        apikey: String,
        hash: String
    ): Single<Response<CharacterDataWrapperDAO>> {
        return Single.just(
            Response.success(
                CharacterDataWrapperDAO( // any interesting object
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        )
    }
}
