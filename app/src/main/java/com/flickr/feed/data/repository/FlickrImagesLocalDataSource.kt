package com.flickr.feed.data.repository

import com.flickr.feed.data.model.FlickrImage

/**
 * Created by andrew on 25/10/2017.
 *
 * Local images repository implementation
 */
interface FlickrImagesLocalDataSource : FlickrImagesDataSource {

    /**
     * Store images into local DB
     */
    fun storeImages(images: List<FlickrImage>)

    /**
     * Delete all stored images
     */
    fun deleteAllImages()
}