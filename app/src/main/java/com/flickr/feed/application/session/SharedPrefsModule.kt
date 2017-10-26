package com.flickr.feed.application.session

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by andrew on 26/10/2017.
 */
@Module
class SharedPrefsModule(val context: Context) {

    @Provides
    fun provideSharedPrefs() : SharedPrefs = SharedPrefs(context)
}