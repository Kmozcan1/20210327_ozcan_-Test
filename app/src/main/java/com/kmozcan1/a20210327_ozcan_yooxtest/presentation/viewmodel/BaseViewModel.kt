package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */
abstract class BaseViewModel<ViewStateClass> : ViewModel() {

    // LiveData object with the type of ViewState class that the child ViewModel uses
    val viewState: LiveData<ViewStateClass>
        get() = _viewState
    private val _viewState = MutableLiveData<ViewStateClass>()
    internal fun setViewState(value: ViewStateClass) {
        _viewState.postValue(value!!)
    }

    abstract fun onError(t: Throwable)
}