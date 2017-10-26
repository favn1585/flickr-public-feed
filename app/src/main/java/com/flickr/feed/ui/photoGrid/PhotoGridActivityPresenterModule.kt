package com.flickr.feed.ui.photoGrid

import dagger.Module
import dagger.Provides

/**
 * Created by andrew on 26/10/2017.
 */
@Module
class PhotoGridActivityPresenterModule(val view: PhotoGridContract.View) {

    @Provides
    fun provideView(): PhotoGridContract.View = view
}