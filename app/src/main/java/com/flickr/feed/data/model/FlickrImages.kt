package com.flickr.feed.data.model

/**
 * Created by andrew on 26/10/2017.
 *
 * Flickr public directory images pull result.
 * Raw data look like this:
 *
 * {
 *  "title": "Uploads from everyone",
 *  "link": "https:\/\/www.flickr.com\/photos\/",
 *  "description": "",
 *  "modified": "2017-10-23T17:30:18Z",
 *  "generator": "https:\/\/www.flickr.com",
 *  "items": [[FlickrImage]]
 * }
 */
class FlickrImages {
    val items: ArrayList<FlickrImage> = ArrayList()
}