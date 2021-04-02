package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Data layer model for Product details; /item api call response
 */
data class ProductDetailResponse (
        @Json(name = "ImageUrls") val imageUrls: ImageUrlApiModel,
        @Json(name = "ItemDescriptions") val itemDescriptions: ItemDescriptionsApiModel,
        @Json(name = "Brand") val brand: BrandApiModel,
        @Json(name = "Category") val category: CategoryApiModel,
        @Json(name = "FormattedPrice") val formattedPrice: FormattedPriceApiModel,
        @Json(name = "Colors") val colors: List<ColorApiModel>,
        @Json(name = "Sizes") val sizes: List<SizeApiModel>,
        @Json(name = "ColorSizeQty") val colorSizeQty: List<ColorSizeQuantityApiModel>
        )