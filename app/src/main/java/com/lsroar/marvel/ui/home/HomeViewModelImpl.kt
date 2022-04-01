package com.lsroar.marvel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.lsroar.data.repository.MarvelCharacterListRepository
import com.lsroar.marvel.R
import com.lsroar.marvel.livedata.ObservableLiveData
import com.lsroar.marvel.ui.BaseViewModel
import com.lsroar.marvel.ui.home.entity.CharacterVO
import com.lsroar.timbercrashlytics.TimberCrashlytics
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModelImpl(
    private val marvelRepository: MarvelCharacterListRepository
) : HomeViewModel, BaseViewModel() {

    private val itemToLoad = ObservableLiveData<Int>()
    private val characterList = MutableLiveData<List<CharacterVO>>()
    private val jobs = mutableListOf<Job>()

    override fun characterList(): LiveData<List<CharacterVO>> {
        return characterList
    }

    override fun onItemClick(id: Int) {
        itemToLoad.setValue(id)
    }

    override fun itemToLoad(): LiveData<Int> {
        return itemToLoad
    }

    override fun onScreenLoaded() {
        this.getCharacterList()
    }

    override fun onRetry() {
        this.getCharacterList()
    }

    private fun getCharacterList() {
        this.isLoading.value = true
        this.jobs.add(
            this.viewModelScope.launch {
                try {
                    val charactersList = marvelRepository.characterList()

                    val list = mutableListOf<CharacterVO>()
                    charactersList.data?.results?.forEach { characterDAO ->
                        characterDAO.id?.let { id ->
                            list.add(
                                CharacterVO(
                                    id,
                                    characterDAO.name ?: "",
                                    characterDAO.thumbnail?.path ?: ""
                                )
                            )
                        }
                    }
                    characterList.value = list
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            TimberCrashlytics.logError(throwable, "getCharacterList", "There is an IOException error")
                            error.setValue(R.string.error_io)
                        }
                        is HttpException -> {
                            TimberCrashlytics.logError(throwable, "getCharacterList", "There is a HttpException error")
                            error.setValue(R.string.error_http)
                        }
                        else -> {
                            TimberCrashlytics.logError(throwable, "getCharacterList", "There is an error")
                            error.setValue(R.string.error)
                        }
                    }
                } finally {
                    isLoading.value = false
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()

        this.jobs.forEach { job ->
            job.cancel()
        }
    }
}
