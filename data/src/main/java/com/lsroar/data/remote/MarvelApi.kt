package com.lsroar.data.remote

import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun characters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Response<CharacterDataWrapperDAO>

    @GET("/v1/public/characters/{id}")
    fun characterDetail(
        @Path("id") id: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Single<Response<CharacterDataWrapperDAO>>
}
