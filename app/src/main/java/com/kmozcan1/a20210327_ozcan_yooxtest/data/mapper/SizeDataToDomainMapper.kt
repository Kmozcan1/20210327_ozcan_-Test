package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.SizeApiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ColorVariant
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Size
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [SizeApiModel] data model into [Size] domain model.
 */
class SizeDataToDomainMapper @Inject constructor() : Mapper<SizeApiModel, Size> {
    /** Maps [SizeApiModel] object into [ColorVariant] object*/
    override fun map(inModel: SizeApiModel): Size = inModel.run {
        Size(id = id, name = name)
    }

    /** Maps each member of a list of [SizeApiModel] objects
     * into a list of [Size] objects using [SizeDataToDomainMapper.map]*/
    override fun transform(inList: List<SizeApiModel>): List<Size> = inList.map { map(it) }
}