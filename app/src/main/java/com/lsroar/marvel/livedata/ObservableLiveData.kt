package com.lsroar.marvel.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * <p>
 * Copyright (c) 2022 by Luis Salvador Roa Rodriguez. All rights reserved.
 * </p>
 *
 * @author <a href=“mailto:realluis85@gmail.com”>Luis Salvador Roa Rodriguez</a>
 */
open class ObservableLiveData<T> : MutableLiveData<T>() {

    companion object {
        val NOT_SET = Any().hashCode()
    }

    @Volatile
    private var mData = NOT_SET

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(
            owner
        ) { t ->
            if (mData != t.hashCode()) {
                mData = t.hashCode()
                observer.onChanged(t)
            }
        }
    }

    override fun postValue(value: T) {
        mData = NOT_SET
        super.postValue(value)
    }

    override fun setValue(value: T) {
        mData = NOT_SET
        super.setValue(value)
    }
}
