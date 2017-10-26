package com.flickr.feed.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.flickr.feed.data.db.DBConfig


/**
 * Created by andrew on 26/10/2017.
 *
 * Flickr public directory single image pull result.
 * Raw data look like this:
 *
 * {
 *  "title": "Junior Parkrun Page Park",
 *  "link": "https:\/\/www.flickr.com\/photos\/155383021@N02\/37164615153\/",
 *  "media": [FlickrImageMedia],
 *  "date_taken": "2017-10-15T10:11:39-08:00",
 *  "description": " <p><a href=\"https:\/\/www.flickr.com\/people\/155383021@N02\/\">j<\/a><\/p>",
 *  "published": "2017-10-23T17:30:16Z",
 *  "author": "nobody@flickr.com (\"jonnynashcash\")",
 *  "author_id": "155383021@N02",
 *  "tags": ""
 * }
 */
@Entity(tableName = DBConfig.TABLE_FLICKR_IMAGES)
data class FlickrImage(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var title: String = "",
        var date_taken: String = "",
        var description: String = "",
        var published: String = "",
        var link: String = "",
        var media_url: String = "") {

    @Ignore
    var media: FlickrImageMedia = FlickrImageMedia()
}