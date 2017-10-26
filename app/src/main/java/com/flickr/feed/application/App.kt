package com.flickr.feed.application

import android.app.Application
import com.flickr.feed.BuildConfig
import com.flickr.feed.data.repository.DaggerFlickrImagesRepositoryComponent
import com.flickr.feed.data.repository.FlickrImagesRepositoryComponent
import com.flickr.feed.network.NetworkModule

/**
 * Created by andrew on 25/10/2017.
 *
 * Main application class
 */

class App : Application() {

    lateinit var mFlickrImagesRepositoryComponent: FlickrImagesRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    fun initDI() {
        mFlickrImagesRepositoryComponent = DaggerFlickrImagesRepositoryComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(BuildConfig.BASEURL))
                .build()
    }
}
