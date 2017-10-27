package com.flickr.feed.data.repository

import com.flickr.feed.data.db.FlickrImagesDao
import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by andrew on 25/10/2017.
 *
 * Local images repository implementation
 */
open class FlickrImagesLocalDataSourceImpl
@Inject constructor(val flickrImagesDao: FlickrImagesDao) : FlickrImagesLocalDataSource {

    override fun storeImages(images: List<FlickrImage>) {
        flickrImagesDao.insertImages(images)
    }

    override fun deleteAllImages() {
        flickrImagesDao.dropImages()
    }

    override fun getImages(): Single<List<FlickrImage>> = flickrImagesDao.getImages()
}