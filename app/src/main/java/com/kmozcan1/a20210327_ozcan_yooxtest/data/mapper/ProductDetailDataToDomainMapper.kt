package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ColorApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ColorSizeQuantityApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductDetailResponse
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorVariant
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Size
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [ProductDetailResponse] data model into [ProductDetail] domain model.
 */
class ProductDetailDataToDomainMapper @Inject constructor(
        private val colorVariantMapper: ColorVariantDataToDomainMapper,
        private val sizeMapper: SizeDataToDomainMapper,
        private val colorSizeQuantityMapper: ColorSizeQuantityDataToDomainMapper) :
        Mapper<ProductDetailResponse, ProductDetail> {
    /** Maps [ProductDetailResponse] object into [ProductDetail] object*/
    override fun map(inModel: ProductDetailResponse): ProductDetail =
            inModel.run {
                ProductDetail(brand = brand.name, category = category.name,
                        formattedFullPrice = formattedPrice.formattedPrice,
                        formattedDiscountedPrice = formattedPrice.discountedPrice,
                        colorVariantList = colorVariantMapper.transform(colors),
                        sizeList = sizeMapper.transform(sizes),
                        productInfoList = itemDescriptions.productInfo,
                        colorSizeQuantityList = colorSizeQuantityMapper.transform(colorSizeQty),
                        imageUrlList = imageUrls.normal
                )
    }

    /** Maps each member of a list of [ProductApiModel] objects
     * into a list of [ProductDetail] objects using [ProductDetailDataToDomainMapper.map]*/
    override fun transform(inList: List<ProductDetailResponse>): List<ProductDetail> =
            inList.map { map(it) }

    private fun createColorSizeMap (colorSizeQty: List<ColorSizeQuantityApiModel>) {
        val colorSizeMap = HashMap<String, List<Int>>()
        for (item in colorSizeQty) {
            item.colorCode
        }
    }
}