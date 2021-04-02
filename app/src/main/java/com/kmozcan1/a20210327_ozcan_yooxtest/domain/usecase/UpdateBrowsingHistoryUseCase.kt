package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.CompletableUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * UseCase class that calls [BrowsingHistoryRepository.updateBrowsingHistory] to retrieve
 * the browsing history list and builds the [Completable] observable that will be observed
 * from the presentation layer
 */
class UpdateBrowsingHistoryUseCase @Inject constructor(
        private val browsingHistoryRepository: BrowsingHistoryRepository
) : CompletableUseCase<UpdateBrowsingHistoryUseCase.Params>() {
    data class Params(val browsingHistory: BrowsingHistory)

    override fun buildObservable(params: Params?): Completable {
        return browsingHistoryRepository
                .updateBrowsingHistory(params!!.browsingHistory)
    }

}