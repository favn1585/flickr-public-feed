package com.flickr.feed.network.imageLoading

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.flickr.feed.R

/**
 * Created by andrew on 26/10/2017.
 *
 * Main app Glide module
 */
@GlideModule
class FlickrGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context?, builder: GlideBuilder) {
        val requestOptions = RequestOptions()
                .placeholder(R.color.image_placeholder)
                .error(R.color.image_placeholder)
                .fallback(R.color.image_placeholder)
                .downsample(DownsampleStrategy.AT_LEAST)
                .centerCrop()

        builder.setDefaultRequestOptions(requestOptions)
        super.applyOptions(context, builder)
    }

    override fun isManifestParsingEnabled(): Boolean = false
}