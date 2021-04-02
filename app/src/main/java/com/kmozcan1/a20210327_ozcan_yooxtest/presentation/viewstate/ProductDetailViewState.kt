package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate

import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductDetailUiModel

/**
 * Created by Kadir Mert Özcan on 31-Mar-21.
 */
data class ProductDetailViewState (
        val state: State,
        val errorMessage: String? = null,
        val productDetail: ProductDetailUiModel? = null) {

    companion object {
        fun error(e: Throwable): ProductDetailViewState = ProductDetailViewState(
                state = State.ERROR,
                errorMessage = e.message
        )

        fun initial(): ProductDetailViewState = ProductDetailViewState(
                state = State.INITIAL
        )

        fun loading(): ProductDetailViewState = ProductDetailViewState(
                state = State.LOADING
        )

        fun browsingHistoryUpdated(): ProductDetailViewState = ProductDetailViewState(
                state = State.BROWSING_HISTORY_UPDATED
        )

        fun productDetailResult(productDetail: ProductDetailUiModel):
                ProductDetailViewState = ProductDetailViewState(
                state = State.PRODUCT_DETAIL_RESULT,
                productDetail = productDetail
        )
    }

    enum class State {
        ERROR,
        INITIAL,
        LOADING,
        BROWSING_HISTORY_UPDATED,
        PRODUCT_DETAIL_RESULT
    }
}