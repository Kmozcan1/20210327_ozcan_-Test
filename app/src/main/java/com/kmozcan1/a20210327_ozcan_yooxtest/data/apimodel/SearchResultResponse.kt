package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 30-Mar-21.
 *
 *
 */
data class SearchResultResponse (
    @Json(name = "Items") val products: List<ProductApiModel>
)