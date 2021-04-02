package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
data class ColorVariantUiModel(
        val name: String,
        val rgb: String,
        val colorCode: String,
        val imgUrlList: MutableList<String>,
        val availableSizeList: MutableList<String?>
)