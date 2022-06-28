package com.example.datalib.utils

//import android.content.Context
//import android.util.SparseArray
//import at.huber.youtubeExtractor.VideoMeta
//import at.huber.youtubeExtractor.YouTubeExtractor
//import at.huber.youtubeExtractor.YtFile
//import com.example.datalib.utils.ExoPlayerManager
//
//class Extractor(context: Context) : YouTubeExtractor(context) {
//    override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?) {
//        if (ytFiles != null) {
//            val itag = 22
//            val downloadUrl: String = ytFiles.get(itag).url
//            ExoPlayerManager.playVideo(downloadUrl)
//        }
//    }
//}