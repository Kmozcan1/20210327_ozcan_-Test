package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ColorSizeQuantityApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorSizeQuantity
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorVariant
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [ColorSizeQuantityApiModel] data model into [ColorVariant] domain model.
 */
class ColorSizeQuantityDataToDomainMapper @Inject constructor() :
        Mapper<ColorSizeQuantityApiModel, ColorSizeQuantity> {
    /** Maps [ColorSizeQuantityApiModel] object into [ColorSizeQuantity] object */
    override fun map(inModel: ColorSizeQuantityApiModel): ColorSizeQuantity = inModel.run {
        ColorSizeQuantity(colorCode = colorCode, sizeId = sizeId)
    }

    /** Maps each member of a list of [ColorSizeQuantityApiModel] objects
     * into a list of [ColorSizeQuantity] objects using [ColorSizeQuantityDataToDomainMapper.map] */
    override fun transform(inList: List<ColorSizeQuantityApiModel>): List<ColorSizeQuantity> =
            inList.map { map(it) }
}