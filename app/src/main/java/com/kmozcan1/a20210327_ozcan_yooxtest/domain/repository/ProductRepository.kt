package com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Interface for the repository encapsulates the logic required to make related api calls
 */
interface ProductRepository {
    fun getProductList(productSortType: ProductSortType): Single<List<Product>>
    fun getProductDetail(): Single<ProductDetail>
}