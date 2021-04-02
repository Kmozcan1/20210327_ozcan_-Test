package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDetailDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
        private val getProductDetailUseCase: GetProductDetailUseCase,
        private val productDetailDomainToUiMapper: ProductDetailDomainToUiMapper
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

    override fun onError(t: Throwable) {
        setViewState(ProductDetailViewState.error(t))
    }

}