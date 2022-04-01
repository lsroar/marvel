package com.lsroar.data.di

import com.lsroar.data.repository.MarvelCharacterDetailRepository
import com.lsroar.data.repository.MarvelCharacterListRepository
import com.lsroar.data.repository.MarvelRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single(named("marvel_character_list")) { MarvelRepositoryImpl(get(named("marvel_api"))) } bind (MarvelCharacterListRepository::class)
    single(named("marvel_character_detail")) { MarvelRepositoryImpl(get(named("marvel_api"))) } bind (MarvelCharacterDetailRepository::class)
}.plus(retrofitModule)
