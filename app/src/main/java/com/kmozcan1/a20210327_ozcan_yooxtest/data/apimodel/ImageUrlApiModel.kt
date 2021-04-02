package com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 *
 *
 */
data class ImageUrlApiModel (
    @Json(name = "Normal") val normal: List<String>)