package com.flickr.feed.data.repository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by andrew on 25/10/2017.
 */
@Module
class FlickrImagesRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(dataSource: FlickrImagesLocalDataSource)
            : FlickrImagesDataSource = dataSource

    @Provides
    @Singleton
    fun provideRemoteDataSource(dataSource: FlickrImagesRemoteDataSource)
            : FlickrImagesDataSource = dataSource

}