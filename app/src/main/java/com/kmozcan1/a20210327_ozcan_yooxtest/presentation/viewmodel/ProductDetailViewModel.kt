package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.UpdateBrowsingHistoryUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.BrowsingHistoryUiToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDetailDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
        private val getProductDetailUseCase: GetProductDetailUseCase,
        private val updateBrowsingHistoryUseCase: UpdateBrowsingHistoryUseCase,
        private val productDetailDomainToUiMapper: ProductDetailDomainToUiMapper,
        private val browsingHistoryToDomainMapper: BrowsingHistoryUiToDomainMapper
) : BaseViewModel<ProductDetailViewState>() {


    init {
        setViewState(ProductDetailViewState.initial())
    }

    /** Calls the use case's execute method (which builds the observable) and starts
     * and sets the view state upon onSuccess or onError emission */
    fun getProductDetail() {
        setViewState(ProductDetailViewState.loading())
        getProductDetailUseCase.execute(
                GetProductDetailUseCase.Params(),
                onSuccess = { productDetail ->
                    setViewState(ProductDetailViewState
                            .productDetailResult(productDetailDomainToUiMapper.map(productDetail)))
                },
                onError = {
                    onError(it)
                }
        )
    }

    /** Calls the use case's execute method to update product history database */
    fun updateBrowsingHistory(browsingHistory: BrowsingHistoryUiModel) {
        setViewState(ProductDetailViewState.loading())
        updateBrowsingHistoryUseCase.execute(
                UpdateBrowsingHistoryUseCase.Params(
                        browsingHistoryToDomainMapper.map(browsingHistory)),
                onComplete = {
                    setViewState(ProductDetailViewState.browsingHistoryUpdated())
                },
                onError = {
                    onError(it)
                }
        )
    }

    /** Inflates a toast for debugging purposes */
    override fun onError(t: Throwable) {
        setViewState(ProductDetailViewState.error(t))
    }
}