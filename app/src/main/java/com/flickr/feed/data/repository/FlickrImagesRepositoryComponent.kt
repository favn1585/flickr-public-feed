package com.flickr.feed.data.repository

import com.flickr.feed.application.AppModule
import com.flickr.feed.data.db.FlickrImagesDBModule
import com.flickr.feed.network.NetworkModule
import com.flickr.feed.utils.SchedulerModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by andrew on 25/10/2017.
 */
@Singleton
@Component(modules = arrayOf(FlickrImagesRepositoryModule::class, AppModule::class,
        FlickrImagesDBModule::class, NetworkModule::class, SchedulerModule::class))
interface FlickrImagesRepositoryComponent {
    fun provideFlickrImagesRepository() : FlickrImagesRepositoryImpl
}