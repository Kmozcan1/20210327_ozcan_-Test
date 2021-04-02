package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val productDomainToUiMapper: ProductDomainToUiMapper
) : BaseViewModel<ProductListViewState>() {

    init {
        setViewState(ProductListViewState.initial())
    }

    fun getProducts(productSortType: ProductSortType) {
        setViewState(ProductListViewState.loading())
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
        TODO("Not yet implemented")
    }
}