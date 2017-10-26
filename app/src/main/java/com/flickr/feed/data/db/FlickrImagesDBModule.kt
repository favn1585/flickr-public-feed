package com.flickr.feed.data.db

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by andrew on 25/10/2017.
 *
 * DB module
 */
@Module
class FlickrImagesDBModule {

    @Provides
    @Singleton
    fun providesFlickrImagesDB(context: Context): FlickrImagesDB =
            Room.databaseBuilder(context, FlickrImagesDB::class.java, DBConfig.DB_NAME).build()

    @Provides
    @Singleton
    fun providesFlickrImagesDao(flickrImagesDB: FlickrImagesDB): FlickrImagesDao =
            flickrImagesDB.getFlickrImagesDao()
}