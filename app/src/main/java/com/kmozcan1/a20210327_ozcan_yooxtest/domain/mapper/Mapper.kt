package com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Interface class for Domain to UI model mapping.
 */
interface Mapper<in inModel, out outModel> {
    //Takes the domain model as input, returns the UI model
    fun map(inModel: inModel): outModel
    fun transform(inList: List<inModel>): List<outModel>
}