package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 *
 * ColorVariant model for domain layer. Represents the color option for a product
 */
data class ColorVariant(
        val colorCode: String,
        val name: String,
        val rgb: String)