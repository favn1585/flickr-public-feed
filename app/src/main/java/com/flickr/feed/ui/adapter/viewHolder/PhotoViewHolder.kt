package com.flickr.feed.ui.adapter.viewHolder

import android.app.Activity
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.flickr.feed.R
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.network.imageLoading.GlideApp


/**
 * Created by andrew on 26/10/2017.
 */
class PhotoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.ivPhoto)
    lateinit var ivPhoto: ImageView

    @BindView(R.id.tvTitle)
    lateinit var tvTitle: TextView

    @BindView(R.id.vClickContainer)
    lateinit var vClickContainer: View

    init {
        ButterKnife.bind(this, view)
    }

    fun bind(activity: Activity, image: FlickrImage) {
        GlideApp.with(activity).load(image.media_url).into(ivPhoto)
        tvTitle.setText(image.title)

        vClickContainer.setTag(image.link)
    }

    @OnClick(R.id.vClickContainer)
    fun clickOnItem(view: View) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(view.context, Uri.parse(view.tag as String))
    }
}