package com.example.datalib.utils

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Suppress("UNUSED")
object ExoPlayerManager {
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L


    fun initializePlayer(mContext: Context): ExoPlayer? {
        player = ExoPlayer.Builder(mContext)
            .build()
        return player
    }

    fun playVideo(downloadUrl: String) {

        val mediaItem = MediaItem.fromUri(downloadUrl)
        player?.setMediaItem(mediaItem)
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentItem, playbackPosition)
        player?.prepare()
    }

    fun releasePlayer() {
        player?.apply {
            playbackPosition = currentPosition
            currentItem = currentMediaItemIndex
            playWhenReady = playWhenReady
            release()
        }
        player = null
    }

}