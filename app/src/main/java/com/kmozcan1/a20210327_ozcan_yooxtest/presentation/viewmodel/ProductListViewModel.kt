package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val getProductsUseCase: GetProductsUseCase,
        private val productDomainToUiMapper: ProductDomainToUiMapper
) : BaseViewModel<ProductListViewState>() {

    companion object {
        const val SORT_TYPE_KEY = "sortType"
        const val HAS_RETAINED_LIST_KEY = "hasRetainedList"
    }

    init {
        setViewState(ProductListViewState.initial())
    }

    /** Calls the use case's execute method (which builds the observable) and starts
     * and sets the view state upon onSuccess or onError emission */
    fun getProducts(productSortType: ProductSortType) {
        setViewState(ProductListViewState.loading())
        setSortButtonState(productSortType)
        setHasRetainedListState(false)
        getProductsUseCase.execute(
            GetProductsUseCase.Params(productSortType),
            onSuccess = { productList ->
                setViewState(ProductListViewState
                    .productListResult(productDomainToUiMapper.transform(productList)))
            },
            onError = {
                onError(it)
            }
        )
    }

    override fun onError(t: Throwable) {
        setViewState(ProductListViewState.error(t))
    }

    fun setHasRetainedListState(hasRetainedList: Boolean) {
        savedStateHandle[HAS_RETAINED_LIST_KEY] = hasRetainedList
    }

    fun getHasRetainedListState(): Boolean? {
        return savedStateHandle.get<Boolean>(HAS_RETAINED_LIST_KEY)
    }

    private fun setSortButtonState(productSortType: ProductSortType) {
        savedStateHandle[SORT_TYPE_KEY] = productSortType
    }

    fun getSortButtonState(): ProductSortType? {
        return savedStateHandle.get<ProductSortType>(SORT_TYPE_KEY)
    }
}