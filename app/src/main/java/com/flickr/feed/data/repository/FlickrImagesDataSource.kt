package com.flickr.feed.data.repository

import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single

/**
 * Created by andrew on 25/10/2017.
 *
 * Images repository
 */
interface FlickrImagesDataSource {
    fun getImages(): Single<List<FlickrImage>>
}