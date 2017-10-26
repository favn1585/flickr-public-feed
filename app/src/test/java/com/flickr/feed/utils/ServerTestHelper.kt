package com.flickr.feed.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.util.*

/**
 * Created by andrew on 26/10/2017.
 */
class ServerTestHelper {

    companion object {
        val testTitle = UUID.randomUUID().toString()
        val testMedia = UUID.randomUUID().toString()
        val testDate = UUID.randomUUID().toString()
        val testDescription = UUID.randomUUID().toString()

        //Can not be zero
        val testImagesSize = 7

        private fun getTestJson(): String {
            val flickrImageMedia = JSONObject()
            flickrImageMedia.put("m", testMedia)

            val flickrImage = JSONObject()
            flickrImage.put("title", testTitle)
            flickrImage.put("link", "")
            flickrImage.put("media", flickrImageMedia)
            flickrImage.put("date_taken", testDate)
            flickrImage.put("description", testDescription)
            flickrImage.put("published", testDate)
            flickrImage.put("author", testTitle)
            flickrImage.put("author_id", "")
            flickrImage.put("tags", "")

            val flickrImagesArray = JSONArray()
            for (i in 0 until testImagesSize) {
                flickrImagesArray.put(flickrImage)
            }

            val flickrImages = JSONObject()
            flickrImages.put("items", flickrImagesArray)

            return flickrImages.toString()
        }

        val imagesDispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse =
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(getTestJson())
        }
    }
}