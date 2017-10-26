package com.flickr.feed.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single

/**
 * Created by andrew on 25/10/2017.
 */
@Dao
interface FlickrImagesDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertImages(images: List<FlickrImage>)

    @Query("SELECT * FROM " + DBConfig.TABLE_FLICKR_IMAGES)
    fun getImages(): Single<List<FlickrImage>>

    @Query("DELETE FROM " + DBConfig.TABLE_FLICKR_IMAGES)
    fun dropImages()
}