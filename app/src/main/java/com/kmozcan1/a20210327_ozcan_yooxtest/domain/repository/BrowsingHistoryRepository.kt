package com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Interface that encapsulates the logic required to access browsing history related db table
 */
interface BrowsingHistoryRepository {
    fun updateBrowsingHistory(browsingHistory: BrowsingHistory): Completable
    fun getBrowsingHistoryList(): Single<List<BrowsingHistory>>
}