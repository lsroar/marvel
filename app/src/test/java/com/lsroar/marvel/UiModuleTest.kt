package com.lsroar.marvel

import com.lsroar.data.di.repositoryModule
import com.lsroar.data.di.retrofitModule
import com.lsroar.marvel.ui.characterdetail.CharacterDetailViewModel
import com.lsroar.marvel.ui.characterdetail.CharacterDetailViewModelImpl
import com.lsroar.marvel.ui.home.HomeViewModel
import com.lsroar.marvel.ui.home.HomeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModuleTest = module {

    viewModel { HomeViewModelImpl(get()) } bind(HomeViewModel::class)
    viewModel { CharacterDetailViewModelImpl(get()) } bind(CharacterDetailViewModel::class)
}.plus(repositoryModule).plus(retrofitModule)
