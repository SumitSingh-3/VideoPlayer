package com.example.videoplayer.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelLazy
import com.example.datalib.utils.StateConstant
import com.example.videoplayer.R
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.ui.adapter.AdapterType
import com.example.videoplayer.ui.adapter.VideoListAdapter
import com.example.videoplayer.ui.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val viewModel by ViewModelLazy(
        PlayerViewModel::class,
        { viewModelStore },
        { defaultViewModelProviderFactory }
    )

    private val videoListAdapter by lazy {
        VideoListAdapter(AdapterType.API_LIST, {}) { data ->
            activityMainBinding.playScreenFrameLayout.visibility = View.VISIBLE
            viewModel.playVideo(data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        callInitialApiCall()
        observeViewModel()

        activityMainBinding.rvVideo.adapter = videoListAdapter
    }

    private fun callInitialApiCall() {
        viewModel.getVideoList()
    }

    private fun observeViewModel() {
        viewModel.apply {
            videoListUiState.observe(this@MainActivity) {
                when (it.state) {
                    StateConstant.SUCCESS -> {
                        hideProgress()
                        it.data?.let { list ->
                            videoListAdapter.setData(list)
                        }
                    }
                    StateConstant.ERROR -> {
                        hideProgress()
                    }
                    StateConstant.LOADING -> {
                        showProgress()
                    }
                    StateConstant.IDLE -> {
                        hideProgress()
                    }
                }
            }

        }
    }


    private fun showProgress() {
        activityMainBinding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        activityMainBinding.progress.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (viewModel.videoPlayerExpanded.value == true)
            viewModel.updatePlayerVideoState(false)
        else
            finish()
    }
}