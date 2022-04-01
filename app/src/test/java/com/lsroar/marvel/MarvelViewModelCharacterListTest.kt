package com.lsroar.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.lsroar.marvel.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@ExperimentalCoroutinesApi
class MarvelViewModelCharacterListTest : KoinTest {

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(uiModuleTest)
    }

    private val homeViewModel by inject<HomeViewModel>()

    @Test
    fun executeTest(): Unit = mainCoroutineRule.runBlockingTest {
        launch(Dispatchers.Main) {
            homeViewModel.onScreenLoaded()
            val result = homeViewModel.characterList()
            Truth.assertThat(result).isNotNull()
            result.observeForever {
                Truth.assertThat(it).isNotEmpty()
            }
        }
    }
}
