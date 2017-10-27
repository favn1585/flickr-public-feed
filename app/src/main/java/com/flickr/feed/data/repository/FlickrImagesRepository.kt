package com.flickr.feed.data.repository

import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single

/**
 * Created by andrew on 27/10/2017.
 */
interface FlickrImagesRepository {

    fun getImages(): Single<List<FlickrImage>>

    fun refreshImages(): Single<List<FlickrImage>>
}