package com.flickr.feed.ui.photoGrid

import com.flickr.feed.data.model.FlickrImage

/**
 * Created by andrew on 26/10/2017.
 *
 * Photo grid contract
 */
interface PhotoGridContract {
    interface View {
        /**
         * Display flickr images
         */
        fun displayImages(images: List<FlickrImage>)

        /**
         * Display error
         */
        fun displayError()
    }

    interface Presenter {
        /**
         * Get images from local storage or load from the server
         */
        fun getImages()

        /**
         * Reload images from remove server
         */
        fun reloadImages()
    }
}