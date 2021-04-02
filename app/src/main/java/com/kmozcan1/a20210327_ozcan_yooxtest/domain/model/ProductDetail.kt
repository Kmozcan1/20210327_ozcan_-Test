package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 *
 * ProductDetail model for domain layer.
 * Tab spacing is a bit weird in this class due to Android Studio
 */
data class ProductDetail (
        val brand: String,
        val category: String,
        private val formattedFullPrice: String,
        private val formattedDiscountedPrice: String,
        val colorVariantList: List<ColorVariant>,
        val sizeList: List<Size>,
        val productInfoList: List<String>,
        val colorSizeQuantityList: List<ColorSizeQuantity>,
        val imageUrlList: List<String>
        ) {

        val price: String = getPreferredPrice()
        val productInfoString = concentrateProductInfo()
        val colorSizeMap: Map<String, List<Size?>> = createColorSizeMap()


        /** Returns the full price if it is equal to discounted price
         * or the discounted price if otherwise */
        private fun getPreferredPrice() : String {
                return if (formattedFullPrice.filter {it.isDigit()} == formattedDiscountedPrice
                        .filter { it.isDigit() }) { formattedFullPrice }
                else {
                        formattedDiscountedPrice
                }
        }

        /** Combines the elements of [productInfoList] as: first element + empty line + rest of
         * the elements, separated by a newline escape character */
        private fun concentrateProductInfo() : String {
                // First line will be followed by an empty line
                val firstLine = productInfoList[0]
                val remainingLines = productInfoList.subList(1,
                        productInfoList.size).joinToString(separator = "\n")
                return StringBuilder()
                        .append(firstLine)
                        .append("\n\n")
                        .append(remainingLines)
                        .toString()
        }

        /** Creates a map with colorCode as key and list of available [Size]'s as value */
        private fun createColorSizeMap() =
                // Groups by colorCode to have colorCode as key of the map
                colorSizeQuantityList.groupBy { colorSizeQuantity ->
                        colorSizeQuantity.colorCode
                }.mapValues { entity ->
                        // Finds the Size object from sideList that has its id equal to the
                        // colorSizeQuantity item's sizeId
                        entity.value.map { colorSizeQuantity ->
                                sizeList.find { size -> size.id == colorSizeQuantity.sizeId}
                        }
                }

}