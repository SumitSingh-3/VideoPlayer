package com.example.datalib.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object BindingAdapterUtils {

    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        view.load(imageUrl)
    }

    @JvmStatic
    @BindingAdapter("bind:videoDuration")
    fun setVideoDuration(view: TextView, duration: Long?) {

        var timeString = "00"

        duration?.let {
            val hours = duration.div(3600);
            val minutes = (duration.rem(3600)).div(60);
            val seconds = duration.rem(60);

            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        }

        view.text = "Duration: $timeString"
    }

    @JvmStatic
    @BindingAdapter("bind:views")
    fun showViews(view: TextView, count: Int) {
        if (count == 0)
            view.text = "Not played yet"
        else
            view.text = "$count views"
    }

    @JvmStatic
    @BindingAdapter("bind:lastViewed")
    fun lastViewed(view: TextView, date: Date) {
        val dateFormat = SimpleDateFormat("dd/MM - hh:mm a", Locale.getDefault()).format(date)
        view.text = dateFormat
    }

}