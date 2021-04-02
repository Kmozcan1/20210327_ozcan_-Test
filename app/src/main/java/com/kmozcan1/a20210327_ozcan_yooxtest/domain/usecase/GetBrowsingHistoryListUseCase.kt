package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * UseCase class that calls [BrowsingHistoryRepository.getBrowsingHistoryList] to retrieve
 * the browsing history list and builds the [Single] observable that will be observed
 * from the presentation layer
 */
class GetBrowsingHistoryListUseCase @Inject constructor(
        private val browsingHistoryRepository: BrowsingHistoryRepository
) : SingleUseCase<List<BrowsingHistory>, GetBrowsingHistoryListUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<List<BrowsingHistory>> {
        return browsingHistoryRepository
                .getBrowsingHistoryList()
    }

}