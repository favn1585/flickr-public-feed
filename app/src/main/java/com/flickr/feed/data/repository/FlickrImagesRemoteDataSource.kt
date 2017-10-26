package com.flickr.feed.data.repository

import com.flickr.feed.data.api.FlickrApi
import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by andrew on 25/10/2017.
 */
open class FlickrImagesRemoteDataSource @Inject constructor(val retrofit: Retrofit) : FlickrImagesDataSource {

    override fun getImages(): Single<List<FlickrImage>> =
            retrofit.create(FlickrApi::class.java).getImages().map { images -> images.items }
}