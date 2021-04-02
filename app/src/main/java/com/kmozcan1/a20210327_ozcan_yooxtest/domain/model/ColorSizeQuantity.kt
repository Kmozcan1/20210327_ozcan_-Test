package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model


/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Domain layer model that contains color / size pair to show which sizes are available for
 * a product's color variant
 */
data class ColorSizeQuantity(
        val colorCode: String,
        val sizeId: Int)
