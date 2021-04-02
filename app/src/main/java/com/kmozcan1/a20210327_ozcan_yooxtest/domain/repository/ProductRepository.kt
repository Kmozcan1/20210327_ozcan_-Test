package com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 */
interface ProductRepository {
    fun getProductList(productSortType: ProductSortType): Single<List<Product>>
    fun getProductDetail(): Single<ProductDetail>
}