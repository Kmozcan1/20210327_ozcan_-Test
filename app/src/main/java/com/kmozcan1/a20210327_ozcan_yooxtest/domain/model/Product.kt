package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Model class for the products that are listed in the first screen of the application.
 */
data class Product(
        val brand: String,
        val category: String,
        private val formattedFullPrice: String,
        private val formattedDiscountedPrice: String,
        private val code10: String) {
    companion object {
        private const val IMAGE_URL_PREFIX = "https://cdn.yoox.biz/"
        private const val IMAGE_URL_SUFFIX = "_11_f.jpg"
    }

    val price: String = getPreferredPrice()

    val imageUrl: String = generateImageUrl()

    /** Returns the full price if it is equal to discounted price
     * or the discounted price if otherwise */
    private fun getPreferredPrice() : String {
        // Ultimately the same thing as always returning the discounted price,
        // but these were my instructions
        return if (formattedFullPrice.filter {it.isDigit()}
                == formattedDiscountedPrice.filter { it.isDigit() }) {
            formattedFullPrice
        } else {
            formattedDiscountedPrice
        }
    }

    // Returns the image url in the following format:
    // "https://cdn.yoox.biz/" + first two characters of code10 + / + code10 + "_11_f.jpg"
    private fun generateImageUrl() : String {
        return IMAGE_URL_PREFIX + code10.take(2) + "/" + code10 + IMAGE_URL_SUFFIX
    }

}