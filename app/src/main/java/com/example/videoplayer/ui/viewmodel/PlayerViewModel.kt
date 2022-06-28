package com.example.videoplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.datalib.base.BaseViewModel
import com.example.datalib.data.model.PexelsVideoMapedData
import com.example.datalib.domain.useCase.DeleteVideoUseCase
import com.example.datalib.domain.useCase.GetHistoryUseCase
import com.example.datalib.domain.useCase.GetRemoteVideoListUseCase
import com.example.datalib.domain.useCase.SaveVideoHistoryUseCase
import com.example.datalib.utils.StateConstant
import com.example.datalib.utils.UIStateResponse
import com.example.datalib.utils.VideoHistoryFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getRemoteVideoListUseCase: GetRemoteVideoListUseCase,
    private val saveVideoHistoryUseCase: SaveVideoHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase
) : BaseViewModel() {

    private val _videoListUiState by lazy {
        MutableLiveData<UIStateResponse<List<PexelsVideoMapedData>>>(UIStateResponse(StateConstant.IDLE))
    }

    val videoListUiState: LiveData<UIStateResponse<List<PexelsVideoMapedData>>> by lazy { _videoListUiState }

    private val _selectedVideo by lazy { MutableLiveData<PexelsVideoMapedData>() }
    val selectedVideo: LiveData<PexelsVideoMapedData> by lazy { _selectedVideo }

    private val _videoHistoryUiState by lazy { MutableLiveData<List<PexelsVideoMapedData>>() }
    val videoHistoryUiState: LiveData<List<PexelsVideoMapedData>> by lazy { _videoHistoryUiState }

    private var currentFilter: VideoHistoryFilter = VideoHistoryFilter.LAST_PLAYED

    private val _videoPlayerExpanded by lazy { MutableLiveData(false) }
    val videoPlayerExpanded: LiveData<Boolean> by lazy { _videoPlayerExpanded }

    fun getVideoList() {
        _videoListUiState.postValue(
            UIStateResponse(StateConstant.LOADING)
        )

        handleUseCase(getRemoteVideoListUseCase) {
            if (it.isSuccess) {
                _videoListUiState.postValue(
                    UIStateResponse(
                        state = StateConstant.SUCCESS,
                        data = it.getSuccess()
                    )
                )
            } else {
                _videoListUiState.postValue(
                    UIStateResponse(
                        state = StateConstant.ERROR,
                        message = it.getError()?.message
                    )
                )
            }
        }
    }

    fun playVideo(video: PexelsVideoMapedData) {
        _selectedVideo.postValue(video)
        _videoPlayerExpanded.postValue(true)
        saveHistory(video)
    }

    private fun saveHistory(video: PexelsVideoMapedData) {
        handleUseCase(saveVideoHistoryUseCase, video) {
            if (it.isError) {
                Log.e("ERROR : ", it.getError()?.message ?: "")
            } else {
                getVideoFromHistory(currentFilter)
            }
        }
    }

    fun getVideoFromHistory(filter: VideoHistoryFilter) {
        currentFilter = filter
        viewModelScope.launch(Dispatchers.IO) {
            _videoHistoryUiState.postValue(getHistoryUseCase.process(filter))
        }
    }

    fun deleteVide(video: PexelsVideoMapedData) {
        handleUseCase(deleteVideoUseCase, video) {
            if (it.isError) {
                Log.e("ERROR : ", it.getError()?.message ?: "")
            } else {
                getVideoFromHistory(currentFilter)
            }
        }
    }

    fun updatePlayerVideoState(status: Boolean) {
        _videoPlayerExpanded.postValue(status)
    }

}