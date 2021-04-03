package com.kmozcan1.a20210327_ozcan_yooxtest.data.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.SearchResultResponse
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType.*
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * [ProductRepository] implementation
 */
class ProductRepositoryImpl @Inject constructor(
    private val yooxApi: YooxApi,
    private val productDataToDomainMapper: ProductDataToDomainMapper,
    private val productDetailDataToDomainMapper: ProductDetailDataToDomainMapper) : ProductRepository {

    /** Calls the API based on the sorting mode */
    override fun getProductList(productSortType: ProductSortType): Single<List<Product>> {
        // Since deciding which API call to make is a data layer logic, it is handled here
        return when(productSortType) {
            DEFAULT -> {
                yooxApi.getProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            LATEST_ARRIVALS -> {
                yooxApi.getLatestProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            LOW_PRICE -> {
                yooxApi.getLowPriceProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            HIGH_PRICE -> {
                yooxApi.getHighPriceProducts().map { response ->
                    mapProductListResult(response)
                }
            }
        }
    }

    /** Makes the /item api call, and returns the mapped response */
    override fun getProductDetail(): Single<ProductDetail> {
        return yooxApi.getProductDetail().map { response ->
            productDetailDataToDomainMapper.map(response)
        }
    }

    private fun mapProductListResult(response: SearchResultResponse) =
            productDataToDomainMapper.transform(inList = response.products)
}