package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.BaseViewModel
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

    fun getProducts() {
        setViewState(ProductListViewState.loading())
        getProductsUseCase.execute(
            GetProductsUseCase.Params(),
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