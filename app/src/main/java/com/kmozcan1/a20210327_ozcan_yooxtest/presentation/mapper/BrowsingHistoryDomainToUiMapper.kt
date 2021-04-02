package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 *  Maps [BrowsingHistory] domain model to [BrowsingHistoryUiModel].
 */
class BrowsingHistoryDomainToUiMapper @Inject constructor()
    : Mapper<BrowsingHistory, BrowsingHistoryUiModel> {

    /** Maps [BrowsingHistory] object into [BrowsingHistoryUiModel] object*/
    override fun map(inModel: BrowsingHistory): BrowsingHistoryUiModel =
            inModel.run {
                BrowsingHistoryUiModel(brand, category, imageUrl, visitDateTime)
            }

    /** Maps each member of a list of [BrowsingHistory] objects
     * into a list of [BrowsingHistoryUiModel] objects using [BrowsingHistoryDomainToUiMapper.map]*/
    override fun transform(inList: List<BrowsingHistory>): List<BrowsingHistoryUiModel> =
            inList.map { map(it) }
}