package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.mapper.Mapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 *  Maps [BrowsingHistoryUiModel] UI model to [BrowsingHistory].
 */
class BrowsingHistoryUiToDomainMapper @Inject constructor()
    : Mapper<BrowsingHistoryUiModel, BrowsingHistory> {

    /** Maps [BrowsingHistoryUiModel] object into [BrowsingHistory] object*/
    override fun map(inModel: BrowsingHistoryUiModel): BrowsingHistory =
            inModel.run {
                BrowsingHistory(brand, category, imageUrl, visitDateTime)
            }

    /** Maps each member of a list of [BrowsingHistoryUiModel] objects
     * into a list of [BrowsingHistory] objects using [BrowsingHistoryUiToDomainMapper.map]*/
    override fun transform(inList: List<BrowsingHistoryUiModel>): List<BrowsingHistory> =
            inList.map { map(it) }
}