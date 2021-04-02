package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate

import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 */
data class BrowsingHistoryViewState  (
        val state: State,
        val errorMessage: String? = null,
        val browsingHistoryList: List<BrowsingHistoryUiModel>? = null) {

    companion object {
        fun error(e: Throwable): BrowsingHistoryViewState = BrowsingHistoryViewState(
                state = State.ERROR,
                errorMessage = e.message
        )

        fun initial(): BrowsingHistoryViewState = BrowsingHistoryViewState(
                state = State.INITIAL
        )

        fun loading(): BrowsingHistoryViewState = BrowsingHistoryViewState(
                state = State.LOADING
        )

        fun browsingHistoryListResult(browsingHistoryList: List<BrowsingHistoryUiModel>):
                BrowsingHistoryViewState = BrowsingHistoryViewState(
                state = State.BROWSING_HISTORY_LIST_RESULT,
                browsingHistoryList = browsingHistoryList
        )
    }

    enum class State {
        ERROR,
        INITIAL,
        LOADING,
        BROWSING_HISTORY_LIST_RESULT
    }
}