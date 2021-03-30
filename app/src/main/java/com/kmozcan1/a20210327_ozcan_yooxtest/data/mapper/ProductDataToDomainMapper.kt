package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 * Maps the ProductApiModel data model into Product domain model.
 */
class ProductDataToDomainMapper @Inject constructor() : Mapper<ProductApiModel, Product> {
    // Currently mapping everything, but in the future, all the attributes
    // from the API response might be added to the ProductApiModel, but not all might be
    // needed by the Product domain model
    /** Maps [ProductApiModel] object into [Product] object*/
    override fun map(inModel: ProductApiModel): Product = inModel.run {
        Product(brand = brand, category = microCategory, formattedFullPrice = formattedFullPrice,
            formattedDiscountedPrice = formattedDiscountedPrice, code10 = cod10)
    }

    /** Maps each member of a list of [ProductApiModel] objects
     * into a list of [Product] objects using [ProductDataToDomainMapper.map]*/
    override fun transform(inList: List<ProductApiModel>): List<Product> = inList.map { map(it) }
}