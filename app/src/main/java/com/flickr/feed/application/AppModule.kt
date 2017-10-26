package com.flickr.feed.application

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by andrew on 25/10/2017.
 *
 * Application module.
 * Uses for providing application context
 */
@Module
class AppModule(val context: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = context
}