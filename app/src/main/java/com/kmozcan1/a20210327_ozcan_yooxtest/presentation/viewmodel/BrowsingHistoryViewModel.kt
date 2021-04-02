package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetBrowsingHistoryListUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.BrowsingHistoryDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.BrowsingHistoryViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowsingHistoryViewModel @Inject constructor(
        private val getBrowsingHistoryListUseCase: GetBrowsingHistoryListUseCase,
        private val browsingHistoryToUiMapper: BrowsingHistoryDomainToUiMapper
        ) : BaseViewModel<BrowsingHistoryViewState>() {

    init {
        setViewState(BrowsingHistoryViewState.initial())
    }

    /** Calls the use case's execute method (which builds the observable) and starts
     * and acts upon onSuccess or onError emission */
    fun getBrowsingHistoryList() {
        getBrowsingHistoryListUseCase.execute(
                GetBrowsingHistoryListUseCase.Params(),
                onSuccess = { browsingHistoryList ->
                    setViewState(BrowsingHistoryViewState
                            .browsingHistoryListResult(
                                    browsingHistoryToUiMapper.transform(browsingHistoryList)))
                },
                onError = {
                    onError(it)
                }
        )
    }

    override fun onError(t: Throwable) {
        setViewState(BrowsingHistoryViewState.error(t))
    }
}