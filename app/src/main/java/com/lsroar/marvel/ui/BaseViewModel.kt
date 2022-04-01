package com.lsroar.marvel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lsroar.marvel.livedata.ObservableLiveData

open class BaseViewModel : ViewModel() {

    protected val isLoading = MutableLiveData(false)
    protected val error = ObservableLiveData<Int>()

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun error(): LiveData<Int> {
        return error
    }
}
