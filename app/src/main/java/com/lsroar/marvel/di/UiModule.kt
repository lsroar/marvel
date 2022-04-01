package com.lsroar.marvel.di

import com.lsroar.marvel.ui.characterdetail.CharacterDetailViewModel
import com.lsroar.marvel.ui.characterdetail.CharacterDetailViewModelImpl
import com.lsroar.marvel.ui.home.HomeViewModel
import com.lsroar.marvel.ui.home.HomeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {

    viewModel { HomeViewModelImpl(get(named("marvel_character_list"))) } bind(HomeViewModel::class)
    viewModel { CharacterDetailViewModelImpl(get(named("marvel_character_detail"))) } bind(CharacterDetailViewModel::class)
}
