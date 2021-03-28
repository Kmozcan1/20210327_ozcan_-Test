package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */
data class ProductListViewState (val state: State) {

    enum class State {
        ERROR,
        INITIAL,
        LOADING,
        SEARCH_RESULT
    }
}
