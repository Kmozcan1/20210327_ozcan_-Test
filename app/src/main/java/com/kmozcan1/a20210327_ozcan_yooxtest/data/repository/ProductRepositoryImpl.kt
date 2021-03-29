package com.kmozcan1.a20210327_ozcan_yooxtest.data.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YamlApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
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

    override fun getProductList(): Single<List<Product>> {
        return yamlApi.getProducts().map { response ->
            productDataToDomainMapper.transform(inList = response)
        }
    }
}