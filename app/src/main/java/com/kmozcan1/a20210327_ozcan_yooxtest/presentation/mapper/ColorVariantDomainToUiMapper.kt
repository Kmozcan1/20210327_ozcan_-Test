package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorVariant
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Size
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ColorVariantUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 *
 *  Maps [ColorVariant] domain model to its UI model.
 */
class ColorVariantDomainToUiMapper @Inject constructor()
    : Mapper<ColorVariant, ColorVariantUiModel> {
    companion object {
        const val COLOR_CODE_PLACEHOLDER = "COLOR"
    }

    /** Maps [ColorVariant] object into [ColorVariantUiModel] object*/
    override fun map(inModel: ColorVariant): ColorVariantUiModel =
            inModel.run {
                ColorVariantUiModel(name = name, rgb = rgb,
                        imgUrlList = mutableListOf(), availableSizeList = mutableListOf())
            }

    /** Maps each member of a list of [ColorVariant] objects
     * into a list of [ColorVariantUiModel] objects using [ColorVariantUiModel.map]*/
    override fun transform(inList: List<ColorVariant>): List<ColorVariantUiModel> =
            inList.map { map(it) }

    /** Transforms the [ColorVariantUiModel] list into a [ColorVariant] list
     * while generating image urls by replacing "COLOR" string with the color code
     * and adding the available size list */
    fun transformWithColorVariantForProductDetail (inList: List<ColorVariant>,
                                                   imgUrls: List<String>,
                                                   colorSizeMap: HashMap<String, List<Size>>) :
            List<ColorVariantUiModel> =
            inList.map { domainColorVariantObject ->
                map(domainColorVariantObject).apply {
                    // Add image urls
                    for (imageUrl in imgUrls) {
                        imgUrlList.add(imageUrl
                                .replace(COLOR_CODE_PLACEHOLDER, domainColorVariantObject.colorCode))
                    }
                    // Add available size (only adds labels with the current implementation)
                    colorSizeMap[name]?.let { sizeList ->
                        for (size in sizeList) {
                            availableSizeList.add(size.name)
                        }
                    }
                }
            }

}