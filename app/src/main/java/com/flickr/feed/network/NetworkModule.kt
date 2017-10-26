package com.flickr.feed.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by andrew on 26/10/2017.
 *
 * Retrofit module. Provide retrofit client module in [App] or [TestApp] classes
 */

@Module
class NetworkModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return retrofitBuilder.client(OkHttpClient.Builder().build()).build()
    }
}