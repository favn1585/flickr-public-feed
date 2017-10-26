package com.flickr.feed.data.api

import com.flickr.feed.data.model.FlickrImages
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by andrew on 25/10/2017.
 *
 * Flickr images api
 */
interface FlickrApi {

    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    fun getImages(): Single<FlickrImages>
}