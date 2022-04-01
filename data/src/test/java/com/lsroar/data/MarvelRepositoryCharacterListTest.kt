package com.lsroar.data

import com.google.common.truth.Truth
import com.lsroar.data.repository.MarvelCharacterListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@ExperimentalCoroutinesApi
class MarvelRepositoryCharacterListTest : KoinTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(dataTestModule)
    }

    private val marvelRepository by inject<MarvelCharacterListRepository>()

    @Test
    fun executeTest(): Unit = mainCoroutineRule.runBlockingTest {
        launch(Dispatchers.Main) {
            val result = marvelRepository.characterList()
            Truth.assertThat(result).isNotNull()
        }
    }
}
