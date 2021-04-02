package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
data class ColorSizeQuantityApiModel(
        @Json(name = "ColorCode") val colorCode: String,
        @Json(name = "SizeId") val sizeId: Int)
