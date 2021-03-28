package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 *
 * Interface class for Domain to UI model mapping.
 */
interface Mapper<in DomainModel, out UiModel> {
    //Takes the domain model as input, returns the UI model
    fun map(domainModel: DomainModel): UiModel
}