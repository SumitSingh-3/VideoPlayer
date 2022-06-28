package com.example.datalib.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.datalib.utils.Constants.DURATION
import com.example.datalib.utils.Constants.FILE_TYPE
import com.example.datalib.utils.Constants.HEIGHT
import com.example.datalib.utils.Constants.ID
import com.example.datalib.utils.Constants.IMAGE
import com.example.datalib.utils.Constants.LAST_PLAYED_AT
import com.example.datalib.utils.Constants.LINK
import com.example.datalib.utils.Constants.NAME
import com.example.datalib.utils.Constants.PAGE
import com.example.datalib.utils.Constants.PER_PAGE
import com.example.datalib.utils.Constants.PLAY_COUNT
import com.example.datalib.utils.Constants.QUALITY
import com.example.datalib.utils.Constants.URL
import com.example.datalib.utils.Constants.USER
import com.example.datalib.utils.Constants.VIDEOS
import com.example.datalib.utils.Constants.VIDEO_FILES
import com.example.datalib.utils.Constants.WIDTH
import com.squareup.moshi.Json
import com.google.gson.annotations.SerializedName
import java.util.*


data class PexelsVideoResponse(
    @Json(name = PAGE)
    val page: Int?,
    @Json(name = PER_PAGE)
    val perPage: Int?,
    @Json(name = VIDEOS)
    val videos: List<Video>?
)

data class Video(
    @Json(name = DURATION)
    val duration: Long?,
    @Json(name = HEIGHT)
    val height: Int?,
    @Json(name = ID)
    val id: Int,
    @Json(name = IMAGE)
    val image: String?,
    @Json(name = URL)
    val url: String?,
    @Json(name = USER)
    val user: User?,
    @Json(name = WIDTH)
    val width: Int?,
    @Json(name = VIDEO_FILES)
    val videoFiles: List<VideoFile>?,
    var isSelected: Boolean = false
)

data class VideoFile(
    @Json(name = ID)
    val id: Int?,
    @Json(name = QUALITY)
    val quality: String?,
    @Json(name = FILE_TYPE)
    val fileType: String?,
    @Json(name = LINK)
    val link: String,
    @Json(name = WIDTH)
    val width: Int?,
    @Json(name = HEIGHT)
    val height: Int?
)

data class User(
    @Json(name = ID)
    val id: Int?,
    @Json(name = NAME)
    val name: String?,
    @Json(name = URL)
    val url: String?
)

@Entity(tableName = "pexelsvideohistory")
data class PexelsVideoMapedData(

    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = NAME)
    val name: String?,
    @ColumnInfo(name = DURATION)
    val duration: Long?,
    @ColumnInfo(name = IMAGE)
    val image: String?,
    @ColumnInfo(name = LINK)
    val link: String?,
    @ColumnInfo(name = PLAY_COUNT)
    var viewCount: Int = 0,
    @ColumnInfo(name = LAST_PLAYED_AT)
    var lastAccessTime: Date = Date()
) {
    @Ignore
    var isSelected: Boolean = false
}

