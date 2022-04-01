package com.lsroar.marvel.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.HttpException
import com.lsroar.data.remote.entity.CharacterDataWrapperDAO
import com.lsroar.data.repository.MarvelCharacterDetailRepository
import com.lsroar.marvel.R
import com.lsroar.marvel.livedata.ObservableLiveData
import com.lsroar.marvel.ui.BaseViewModel
import com.lsroar.marvel.ui.characterdetail.entity.CharacterDetailVO
import com.lsroar.timbercrashlytics.TimberCrashlytics
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class CharacterDetailViewModelImpl(
    private val marvelRepository: MarvelCharacterDetailRepository
) : CharacterDetailViewModel, BaseViewModel() {

    companion object {
        private const val KEY_IMAGE_FORMAT = "/landscape_amazing.jpg"
    }

    private val compositeDisposable = CompositeDisposable()
    private val characterDetail = MutableLiveData<CharacterDetailVO>()
    private val navigation = ObservableLiveData<CharacterDetailNavigation>()

    override fun characterDetail(): LiveData<CharacterDetailVO> {
        return characterDetail
    }

    override fun onExtraValue(id: Int) {
        this.getCharacterDetail(id)
    }

    private fun getCharacterDetail(id: Int) {
        this.isLoading.value = true
        this.compositeDisposable.add(
            marvelRepository.characterDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    this.mapCharacterDetail(it)
                }.onErrorReturn {
                    // here we could return a default value
                    throw it
                }
                .subscribeOn(Schedulers.io())
                .doAfterTerminate {
                    this.isLoading.value = false
                }
                .subscribe(
                    {
                        characterDetail.value = it
                    }, { throwable ->
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
                }
                )
        )
    }

    override fun onExit() {
        this.navigation.setValue(CharacterDetailNavigation.Home)
    }

    override fun navigation(): LiveData<CharacterDetailNavigation> {
        return this.navigation
    }

    private fun mapCharacterDetail(character: CharacterDataWrapperDAO): CharacterDetailVO {
        return character.data!!.results!!.let { characterListDAO ->
            return@let CharacterDetailVO(
                characterListDAO[0].name!!,
                characterListDAO[0].thumbnail!!.path!! + KEY_IMAGE_FORMAT
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        this.compositeDisposable.clear()
        this.compositeDisposable.dispose()
    }
}
