package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
class ProductDetailUiModel (
        val brand: String,
        val category: String,
        val price: String,
        val productInfo: String,
        val colorVariantList: List<ColorVariantUiModel>,
        val sizeList: List<String>
        )