package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Data layer model for the items field of the API response from "/searchresult"
 * Only required fields are listed currently
 */
data class ProductApiModel (
        @Json(name = "Brand") val brand: String,
        @Json(name = "MicroCategory") val microCategory: String,
        @Json(name = "FormattedFullPrice") val formattedFullPrice: String,
        @Json(name = "FormattedDiscountedPrice") val formattedDiscountedPrice: String,
        @Json(name = "Cod10") val cod10: String
        )