package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * UseCase class that calls [ProductRepository.getProductDetail] to retrieve product details
 * and builds the [Single] observable that will be observed from the presentation layer
 */
class GetProductDetailUseCase @Inject constructor(
        private val productRepository: ProductRepository
): SingleUseCase<ProductDetail, GetProductDetailUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<ProductDetail> {
        return productRepository
                .getProductDetail().map { productDetail -> productDetail }
    }
}