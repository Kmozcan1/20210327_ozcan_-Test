package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * UseCase class that calls [ProductRepository.getProductList] to retrieve the list of products
 * and builds the [Single] observable that will be observed from the presentation layer
 */
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
): SingleUseCase<List<Product>, GetProductsUseCase.Params>() {
    data class Params(val productSortType: ProductSortType)

    override fun buildObservable(params: Params?): Single<List<Product>> {
        return productRepository
                .getProductList(params!!.productSortType).map { productList -> productList }
    }
}