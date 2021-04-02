package com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 * Maps the [BrowsingHistoryEntity] data model into [BrowsingHistory] domain model.
 */

class BrowsingHistoryDataToDomainMapper @Inject constructor()
    : Mapper<BrowsingHistoryEntity, BrowsingHistory> {
    /** Maps [BrowsingHistoryEntity] object into [BrowsingHistory] object */
    // These two are essentially the same object, but having different models for each layer
    // is required for the separation of layers and DIP
    override fun map(inModel: BrowsingHistoryEntity): BrowsingHistory = inModel.run {
        BrowsingHistory(code10, brand, category, imageUrl, visitDateTime)
    }

    /** Maps each member of a list of [BrowsingHistoryEntity] objects
     * into a list of [BrowsingHistory] objects using [BrowsingHistoryDataToDomainMapper.map] */
    override fun transform(inList: List<BrowsingHistoryEntity>): List<BrowsingHistory> =
            inList.map { map(it) }
}