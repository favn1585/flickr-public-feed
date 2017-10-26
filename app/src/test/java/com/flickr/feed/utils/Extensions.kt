package com.flickr.feed.utils

import okhttp3.mockwebserver.MockWebServer

/**
 * Created by andrew on 26/10/2017.
 *
 * Some extenision functions for testing purposes
 */
fun MockWebServer.getHost() = "http://$hostName:$port"