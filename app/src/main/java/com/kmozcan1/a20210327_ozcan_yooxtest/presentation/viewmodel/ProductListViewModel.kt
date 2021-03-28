package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.BaseViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor() : BaseViewModel<ProductListViewState>() {
    override fun onError(t: Throwable) {
        TODO("Not yet implemented")
    }
    // TODO: Implement the ViewModel
}