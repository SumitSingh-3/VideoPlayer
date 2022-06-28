package com.example.datalib.utils;

interface CallBacks {
    fun callbackObserver(obj: Object)

    interface playerCallBack {
        fun onItemClickOnItem(albumId: Int)
        fun onPlayingEnd()
    }

}