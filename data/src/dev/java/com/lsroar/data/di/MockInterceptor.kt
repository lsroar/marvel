package com.lsroar.data.di

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class MockInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}
