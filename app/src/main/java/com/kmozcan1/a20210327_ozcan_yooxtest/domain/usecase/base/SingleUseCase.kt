package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Base class for interactors that return [Single] objects
 */
abstract class SingleUseCase<Results, in Params> : Disposable {
    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Abstract function where the interactor class calls methods that handle domain logic
     */
    abstract fun buildObservable(params: Params? = null): Single<Results>

    /**
     * Builds and subscribes the [Single] object, then adds it to the list of disposables
     */
    fun execute(params: Params? = null, onSuccess: Consumer<Results>?= Consumer {  },
                onError: Consumer<Throwable>? = Consumer {  }) {
        disposables += buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(onSuccess)
            .doOnError(onError)
            .subscribe({}, Timber::w)
    }

    override fun dispose() {
        return disposables.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}