package com.flickr.feed.utils

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * Created by andrew on 26/10/2017.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class RunOn(val value: SchedulerContext = SchedulerContext.IO)
