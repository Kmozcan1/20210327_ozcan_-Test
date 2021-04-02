package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.ObserveInternetConnectionUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeInternetConnectionUseCase: ObserveInternetConnectionUseCase
) : BaseViewModel<MainViewState>() {

    /** Calls the use case's execute method (which builds the observable) and starts
     * and sets the view state upon onNext or onError emission */
    fun observeInternetConnection() {
        observeInternetConnectionUseCase.execute(
            onComplete = {},
            onNext = { isConnected ->
                setViewState(MainViewState.connectionChange(isConnected))
            },
            onError = {
                onError(it)
            },
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(MainViewState.error(t))
    }
}