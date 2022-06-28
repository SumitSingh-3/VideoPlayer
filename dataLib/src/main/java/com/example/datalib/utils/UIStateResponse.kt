package com.example.datalib.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class UIStateResponse<out R>(
    val state: StateConstant,
    val message: String? = null,
    val data: R? = null,
) : Any()

enum class StateConstant {
    LOADING, IDLE, SUCCESS, ERROR
}

fun <T> MutableLiveData<UIStateResponse<T>>.setSuccess(data: T? = null) =
    postValue(UIStateResponse(state = StateConstant.SUCCESS, data = data))

fun <T> MutableLiveData<UIStateResponse<T>>.setLoading() =
    postValue(UIStateResponse(state = StateConstant.LOADING, data = null))

fun <T> MutableLiveData<UIStateResponse<T>>.setError(message: String? = null) =
    postValue(UIStateResponse(state = StateConstant.ERROR, message = message, data = null))

fun <T> MutableLiveData<UIStateResponse<T>>.setEmpty(message: String? = null) =
    postValue(UIStateResponse(state = StateConstant.IDLE, message = message, data = null))

fun <T> MutableLiveData<UIStateResponse<T>>.data() = value?.data
fun <T> LiveData<UIStateResponse<T>>.data() = value?.data

fun <T> SingleLiveEvent<UIStateResponse<T>>.setSuccess(data: T? = null) =
    postValue(UIStateResponse(state = StateConstant.SUCCESS, data = data))

fun <T> SingleLiveEvent<UIStateResponse<T>>.setLoading() =
    postValue(UIStateResponse(state = StateConstant.LOADING, data = null))

fun <T> SingleLiveEvent<UIStateResponse<T>>.setError(message: String? = null) =
    postValue(UIStateResponse(state = StateConstant.ERROR, message = message, data = null))

fun <T> SingleLiveEvent<UIStateResponse<T>>.setEmpty(message: String? = null) =
    postValue(UIStateResponse(state = StateConstant.IDLE, message = message, data = null))

fun <T> SingleLiveEvent<UIStateResponse<T>>.data() = value?.data