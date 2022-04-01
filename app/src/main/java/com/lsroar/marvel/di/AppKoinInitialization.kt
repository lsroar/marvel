package com.lsroar.marvel.di

import com.lsroar.data.di.repositoryModule
import org.koin.core.module.Module

class AppKoinInitialization {
    companion object {
        fun getModules(): List<Module> {
            return uiModule
                .plus(repositoryModule)
        }
    }
}
