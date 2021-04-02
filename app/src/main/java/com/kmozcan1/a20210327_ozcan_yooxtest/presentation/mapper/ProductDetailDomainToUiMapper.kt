package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductDetailUiModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 *
 * Maps [ProductDetail] domain model to its UI model.
 * Also uses the injected [ColorVariantDomainToUiMapper] object to map ColorVariant domain model
 * to its UI model while also generating image urls for each color variant
 */
class ProductDetailDomainToUiMapper @Inject constructor(
        private val colorVariantMapper: ColorVariantDomainToUiMapper) :
        Mapper<ProductDetail, ProductDetailUiModel> {
    /** Maps [ProductDetail] object into [ProductDetailUiModel] object*/
    override fun map(inModel: ProductDetail): ProductDetailUiModel = inModel.run {
        ProductDetailUiModel(
                brand = brand, category = category,
                price = price, productInfo = productInfoString,
                colorVariantList = colorVariantMapper
                        .transformWithColorVariantForProductDetail(
                                colorVariantList, imageUrlList, colorSizeMap),
                sizeList = sizeList.map { it.name })
    }

    /** Maps each member of a list of [ProductDetail] objects
     * into a list of [ProductDetailUiModel] objects using [ProductDetailDomainToUiMapper.map]*/
    override fun transform(inList: List<ProductDetail>): List<ProductDetailUiModel> =
            inList.map { map(it) }
}