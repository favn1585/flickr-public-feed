package com.flickr.feed.ui.photoGrid

import com.flickr.feed.application.session.SharedPrefsModule
import com.flickr.feed.data.repository.FlickrImagesRepositoryComponent
import com.flickr.feed.utils.ActivityScope
import com.flickr.feed.utils.SchedulerModule
import dagger.Component

/**
 * Created by andrew on 26/10/2017.
 */
@ActivityScope
@Component(modules = arrayOf(PhotoGridActivityPresenterModule::class, SchedulerModule::class,
        SharedPrefsModule::class),
        dependencies = arrayOf(FlickrImagesRepositoryComponent::class))
interface PhotoGridComponent {
    fun inject(photoGridActivity: PhotoGridActivity)
}