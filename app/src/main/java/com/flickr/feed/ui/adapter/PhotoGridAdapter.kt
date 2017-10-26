package com.flickr.feed.ui.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.flickr.feed.R
import com.flickr.feed.data.model.FlickrImage
import com.flickr.feed.ui.adapter.viewHolder.PhotoViewHolder

/**
 * Created by andrew on 26/10/2017.
 */
class PhotoGridAdapter(val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var images: List<FlickrImage> = arrayListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as PhotoViewHolder).bind(activity, images[position])
    }

    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.itm_photo, parent, false)
        return PhotoViewHolder(view)
    }

    fun setImages(images: List<FlickrImage>) {
        this.images = images
        notifyDataSetChanged()
    }
}