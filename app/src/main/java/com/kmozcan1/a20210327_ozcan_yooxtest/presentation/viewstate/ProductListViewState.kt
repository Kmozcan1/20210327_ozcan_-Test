package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate

import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */
data class ProductListViewState (
    val state: State,
    val errorMessage: String? = null,
    val productList: List<ProductUiModel>? = null) {

    companion object {
        fun error(e: Throwable): ProductListViewState = ProductListViewState(
                state = State.ERROR,
                errorMessage = e.message
        )

        fun initial(): ProductListViewState = ProductListViewState(
                state = State.INITIAL
        )

        fun loading(): ProductListViewState = ProductListViewState(
                state = State.LOADING
        )

        fun productListResult(productList: List<ProductUiModel>):
                ProductListViewState = ProductListViewState(
                state = State.LIST_RESULT,
                productList = productList
        )
    }

    enum class State {
        ERROR,
        INITIAL,
        LOADING,
        LIST_RESULT
    }
}
