package com.kmozcan1.a20210327_ozcan_yooxtest.data.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YamlApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.SearchResultResponse
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType.*
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * [ProductRepository] implementation
 */
class ProductRepositoryImpl @Inject constructor(
    private val yamlApi: YamlApi,
    private val productDataToDomainMapper: ProductDataToDomainMapper): ProductRepository {

    /** Calls the API based on the sorting mode
     * Since deciding which API call to make is a data layer logic, it is handled here */
    override fun getProductList(productSortType: ProductSortType): Single<List<Product>> {
        return when(productSortType) {
            DEFAULT -> {
                yamlApi.getProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            LATEST_ARRIVALS -> {
                yamlApi.getLatestProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            LOW_PRICE -> {
                yamlApi.getLowPriceProducts().map { response ->
                    mapProductListResult(response)
                }
            }
            HIGH_PRICE -> {
                yamlApi.getHighPriceProducts().map { response ->
                    mapProductListResult(response)
                }
            }
        }
    }

    private fun mapProductListResult(response: SearchResultResponse) =
            productDataToDomainMapper.transform(inList = response.products)
}