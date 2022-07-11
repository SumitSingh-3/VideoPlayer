package com.example.videoplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import androidx.media3.common.util.Util
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.utils.ExoPlayerManager
import com.example.datalib.utils.VideoHistoryFilter
import com.example.videoplayer.R
import com.example.videoplayer.databinding.FragmentPlayerBinding
import com.example.videoplayer.ui.adapter.AdapterType
import com.example.videoplayer.ui.adapter.VideoListAdapter
import com.example.videoplayer.ui.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    lateinit var motionLayout: MotionLayout

    private val viewModel by ViewModelLazy(
        PlayerViewModel::class,
        { requireActivity().viewModelStore },
        { defaultViewModelProviderFactory }
    )

    private val videoListAdapter by lazy {
        VideoListAdapter(AdapterType.HISTORY_LIST, ::onDeleteVide) {
            viewModel.playVideo(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)
        motionLayout = binding.rootLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        observeViewModel()

        binding.recyclerviewVideo.adapter = videoListAdapter

    }

    private fun observeViewModel() {
        viewModel.apply {
            videoHistoryUiState.observe(viewLifecycleOwner) {
                it?.let {
                    videoListAdapter.setData(it)

                    if (it.isEmpty())
                        binding.tvNoData.visibility = View.VISIBLE
                    else
                        binding.tvNoData.visibility = View.GONE
                }
            }

            selectedVideo.observe(viewLifecycleOwner) {
                motionLayout.transitionToStart()
                binding.selectedData = it
                it?.link?.let {
                    ExoPlayerManager.playVideo(it)
                }
            }

            videoPlayerExpanded.observe(viewLifecycleOwner) {
                if (!it)
                    motionLayout.transitionToEnd()
            }
        }
    }

    private fun onDeleteVide(video: PexelsVideoMapedData) {
        viewModel.deleteVide(video)
    }

    private fun setListener() {

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (currentId) {
                    R.id.start -> viewModel.updatePlayerVideoState(true)
                    R.id.end -> viewModel.updatePlayerVideoState(false)
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }
        })

        binding.chipGroup.setOnCheckedChangeListener { _, checkedIds ->
            when (checkedIds) {
                R.id.chip_most_viewed -> {
                    viewModel.getVideoFromHistory(VideoHistoryFilter.MOST_VIEWED)
                }
                R.id.chip_last_viewed -> {
                    viewModel.getVideoFromHistory(VideoHistoryFilter.LAST_PLAYED)
                }
            }
        }

        binding.chipLastViewed.isChecked = true
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            binding.playerView.player = ExoPlayerManager.initializePlayer(requireContext())
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) {
            binding.playerView.player = ExoPlayerManager.initializePlayer(requireContext())
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            ExoPlayerManager.releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            ExoPlayerManager.releasePlayer()
        }
    }
}