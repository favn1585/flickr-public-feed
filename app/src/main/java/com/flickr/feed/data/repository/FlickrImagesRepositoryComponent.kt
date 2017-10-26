package com.flickr.feed.data.repository

import com.flickr.feed.application.AppModule
import com.flickr.feed.data.db.FlickrImagesDBModule
import com.flickr.feed.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by andrew on 25/10/2017.
 */
@Singleton
@Component(modules = arrayOf(FlickrImagesRepositoryModule::class, AppModule::class,
        FlickrImagesDBModule::class, NetworkModule::class))
interface FlickrImagesRepositoryComponent {
    fun provideFlickrImagesRepository() : FlickrImagesRepository
}