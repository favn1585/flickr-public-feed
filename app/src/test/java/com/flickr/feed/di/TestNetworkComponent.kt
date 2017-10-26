package com.flickr.feed.di

import com.flickr.feed.ApiTest
import com.flickr.feed.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by andrew on 26/10/2017.
 */
@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface TestNetworkComponent {
    fun inject(apiTest: ApiTest)
}
