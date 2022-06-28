package com.example.datalib.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class VideoHistory(

    @PrimaryKey
    val videoId: String = String(),

    @ColumnInfo(name="e_tag")
    val eTag: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String? = null,

    @ColumnInfo(name = "creator_name")
    val creatorName: String? = null,

    @ColumnInfo(name = "published_date")
    val publishedDate: String? = null,

) {

    @ColumnInfo(name = "view_count")
    var viewCount: Int = 0

    @ColumnInfo(name = "last_access_time")
    var lastAccessTime: Date = Date()

}