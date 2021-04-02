package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [BrowsingHistory] domain model into [BrowsingHistoryEntity] data model.
 */

class BrowsingHistoryDomainToDataMapper : Mapper<BrowsingHistory, BrowsingHistoryEntity> {
    /** Maps [BrowsingHistory] object into [BrowsingHistoryEntity] object */
    // These two are essentially the same object, but having different models for each layer
    // is required for the separation of layers and DIP
    override fun map(inModel: BrowsingHistory): BrowsingHistoryEntity = inModel.run {
        BrowsingHistoryEntity(brand, category, image, visitDateTime)
    }

    /** Maps each member of a list of [BrowsingHistoryEntity] objects
     * into a list of [BrowsingHistory] objects using [BrowsingHistoryDomainToDataMapper.map] */
    override fun transform(inList: List<BrowsingHistory>): List<BrowsingHistoryEntity> =
            inList.map { map(it) }
}