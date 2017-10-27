package com.flickr.feed.data.repository

import com.flickr.feed.data.api.FlickrApi
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.utils.RunOn
import com.flickr.feed.utils.SchedulerContext
import io.reactivex.Scheduler
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by andrew on 25/10/2017.
 */
class FlickrImagesRemoteDataSource
@Inject constructor(val retrofit: Retrofit,
                    @RunOn(SchedulerContext.COMPUTATION) private val computationScheduler: Scheduler)
    : FlickrImagesDataSource {

    override fun getImages(): Single<List<FlickrImage>> =
            retrofit.create(FlickrApi::class.java).getImages()
                    .observeOn(computationScheduler)
                    .map { images -> images.items }
}