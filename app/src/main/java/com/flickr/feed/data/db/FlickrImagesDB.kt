package com.flickr.feed.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.flickr.feed.data.model.FlickrImage

/**
 * Created by andrew on 25/10/2017.
 */
@Database(entities = arrayOf(FlickrImage::class), version = 1)
abstract class FlickrImagesDB : RoomDatabase() {
    abstract fun getFlickrImagesDao(): FlickrImagesDao
}