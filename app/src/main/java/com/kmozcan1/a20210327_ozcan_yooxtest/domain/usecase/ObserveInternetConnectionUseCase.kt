package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase

import com.kmozcan1.a20210327_ozcan_yooxtest.domain.manager.InternetManager
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 *
 * UseCase class that emits the changes in Internet state provided by [InternetManager]
 */
class ObserveInternetConnectionUseCase @Inject constructor(
    private val internetManager: InternetManager
): ObservableUseCase<Boolean, ObserveInternetConnectionUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Observable<Boolean> {
        return internetManager.getInternetState()
    }
}