package com.flickr.feed.data.repository

import com.flickr.feed.data.model.FlickrImage
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by andrew on 27/10/2017.
 */
class FlickrImagesRepositoryImpl
@Inject constructor(private val localDataSource: FlickrImagesLocalDataSource,
                    private val remoteDataSource: FlickrImagesDataSource)
    : FlickrImagesRepository{

    override fun getImages(): Single<List<FlickrImage>> {
        return localDataSource.getImages()
                .filter({ v -> !v.isEmpty() })
                .switchIfEmpty(refreshImages())
    }

    override fun refreshImages(): Single<List<FlickrImage>> {
        return remoteDataSource.getImages()
                .doAfterSuccess { images ->
                    images.forEach { i -> i.media_url = i.media.m }
                    localDataSource.deleteAllImages()
                    localDataSource.storeImages(images)
                }
    }
}