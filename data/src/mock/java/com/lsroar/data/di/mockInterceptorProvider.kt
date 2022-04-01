package com.lsroar.data.di

import android.content.Context
import okhttp3.Interceptor

fun provideMockInterceptor(context: Context): Interceptor? {
    return MockInterceptor(context)
}
