package com.example.videoplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.videoplayer.databinding.VideoListItemBinding

class VideoListAdapter(
    private val listType: AdapterType,
    private val onDeleteClick: (PexelsVideoMapedData) -> Unit,
    private val onClick: (PexelsVideoMapedData) -> Unit
) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    private val videoList = ArrayList<PexelsVideoMapedData>()
    private var selectedItem: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            VideoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class VideoViewHolder(
        private val binding: VideoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PexelsVideoMapedData) {
            binding.item = data

            binding.root.setOnClickListener {
                if (listType == AdapterType.API_LIST) {
                    data.isSelected = true
                    notifyItemChanged(bindingAdapterPosition)
                    onClick.invoke(data)

                    if (selectedItem != -1) {
                        videoList[selectedItem].isSelected = false
                        notifyItemChanged(selectedItem)
                    }

                    selectedItem = bindingAdapterPosition
                }
            }

            binding.ivDelete.setOnClickListener {
                onDeleteClick.invoke(data)
            }

            when (listType) {
                AdapterType.HISTORY_LIST -> {
                    binding.ivPlaying.visibility = View.GONE
                    binding.tvViews.visibility = View.VISIBLE
                    binding.tvTime.visibility = View.VISIBLE
                    binding.ivDelete.visibility = View.VISIBLE
                }
                AdapterType.API_LIST -> binding.tvViews.visibility = View.GONE
            }
        }
    }

    fun setData(items: List<PexelsVideoMapedData>) {
        videoList.clear()
        videoList.addAll(items)
        notifyDataSetChanged()
    }
}

enum class AdapterType {
    API_LIST,
    HISTORY_LIST
}