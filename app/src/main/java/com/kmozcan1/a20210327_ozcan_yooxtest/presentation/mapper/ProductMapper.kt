package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Maps Product domain model to its UI model.
 * This is used because UI gets different price value based on a simple condition
 * and it doesn't need the code10 attribute
 */
class ProductMapper : Mapper<Product, ProductUiModel> {
    override fun map(domainModel: Product): ProductUiModel = domainModel.run {
        ProductUiModel(brand = brand, category = category,
                price = price, imageUrl = imageUrl)
    }
}