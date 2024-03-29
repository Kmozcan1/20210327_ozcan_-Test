package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Maps [Product] domain model to its UI model.
 */
class ProductDomainToUiMapper @Inject constructor() : Mapper<Product, ProductUiModel> {
    /** Maps [Product] object into [ProductUiModel] object*/
    override fun map(inModel: Product): ProductUiModel = inModel.run {
        ProductUiModel(code10 = code10, brand = brand, category = category,
                price = price, imageUrl = imageUrl)
    }

    /** Maps each member of a list of [Product] objects
     * into a list of [ProductUiModel] objects using [ProductDomainToUiMapper.map]*/
    override fun transform(inList: List<Product>): List<ProductUiModel> = inList.map { map(it) }
}