package com.lsroar.data.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lsroar.data.BuildConfig
import com.lsroar.data.remote.MarvelApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {

    single(named("mockInterceptor")) { provideMockInterceptor(get()) }

    single {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mockInterceptor: Interceptor = get(named("mockInterceptor"))

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)

        builder.addInterceptor(mockInterceptor)
        builder.build()
    }

    single {
        GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()
    }

    single(named("retrofit_marvel")) {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.MARVEL_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single(named("marvel_api")) {
        val retrofit: Retrofit = get(named("retrofit_marvel"))
        retrofit.create(MarvelApi::class.java)
    }
}
