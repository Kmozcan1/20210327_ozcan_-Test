package com.kmozcan1.a20210327_ozcan_yooxtest.data.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDomainToDataMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * [BrowsingHistoryRepository] implementation
 */
class BrowsingHistoryRepositoryImpl @Inject constructor(
        private val browsingHistoryDao: BrowsingHistoryDao,
        private val browsingHistoryToDomainMapper: BrowsingHistoryDataToDomainMapper,
        private val browsingHistoryToDataMapper: BrowsingHistoryDomainToDataMapper,
)
    : BrowsingHistoryRepository {

    /** Accesses dao to insert a new browsing history entry */
    override fun updateBrowsingHistory(browsingHistory: BrowsingHistory): Completable {
        return browsingHistoryDao
                .insertBrowsingHistory(browsingHistoryToDataMapper.map(browsingHistory))
    }

    /** Accesses dao to retrieve the browsing history list */
    override fun getBrowsingHistory(): Single<List<BrowsingHistory>> {
        return browsingHistoryDao.getBrowsingHistory().map { browsingHistoryList ->
            browsingHistoryToDomainMapper.transform(browsingHistoryList)
        }
    }
}