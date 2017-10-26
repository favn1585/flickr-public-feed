package com.flickr.feed.utils

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by andrew on 26/10/2017.
 *
 * Main Schedulers module
 */
@Module
class SchedulerModule {

    @Provides
    @RunOn(SchedulerContext.IO)
    internal fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @RunOn(SchedulerContext.UI)
    internal fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}