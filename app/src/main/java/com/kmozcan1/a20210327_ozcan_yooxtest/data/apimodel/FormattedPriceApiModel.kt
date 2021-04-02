package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
data class FormattedPriceApiModel (
        @Json(name = "FullPrice") val formattedPrice: String,
        @Json(name = "DiscountedPrice") val discountedPrice: String)