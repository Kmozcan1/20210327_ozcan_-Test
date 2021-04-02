package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Presentation layer model for the products that are listed in the first screen of the app.
 */
data class ProductUiModel(
        val brand: String,
        val category: String,
        val price: String,
        val imageUrl: String)
