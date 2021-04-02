package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ColorApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorVariant
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [ColorApiModel] data model into [ColorVariant] domain model.
 */
class ColorVariantDataToDomainMapper @Inject constructor() : Mapper<ColorApiModel, ColorVariant> {
    /** Maps [ColorApiModel] object into [ColorVariant] object*/
    override fun map(inModel: ColorApiModel): ColorVariant = inModel.run {
        ColorVariant(colorCode = colorCode, name = name, rgb = rgb)
    }

    /** Maps each member of a list of [ColorApiModel] objects
     * into a list of [Product] objects using [ProductDataToDomainMapper.map]*/
    override fun transform(inList: List<ColorApiModel>): List<ColorVariant> = inList.map { map(it) }
}