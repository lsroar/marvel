package com.lsroar.data

import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import com.lsroar.data.repository.MarvelCharacterListRepository
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class MarvelRepositoryCharacterDetailTest : KoinTest {

    private val marvelRepository by inject<MarvelCharacterListRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(dataTestModule)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getCharacterDetail() {
        val testObserver = TestObserver<CharacterDataWrapperDAO>()
        marvelRepository.characterDetail(0).subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValue { value ->
            value.data == null
        }
    }
}
