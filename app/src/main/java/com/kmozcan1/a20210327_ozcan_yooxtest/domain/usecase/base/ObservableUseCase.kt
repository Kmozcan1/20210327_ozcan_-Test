package com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 * Base class for interactors that return [Observable] objects
 * */
abstract class ObservableUseCase<Result, in Params> : Disposable {
    private var disposables = CompositeDisposable()

    /**
     * Abstract function where the interactor class calls methods that handle domain logic
     */
    abstract fun buildObservable(params: Params? = null): Observable<Result>

    /**
     * Builds and subscribes the [Observable] object, then adds it to the list of disposables
     */
    fun execute(
        params: Params? = null,
        onComplete: () -> Unit = { },
        onNext: Consumer<Result>? = Consumer { },
        onError: Consumer<Throwable>? = Consumer { },
        onSubscribe: Consumer<in Disposable>? = Consumer { }
    ) {
        val disposable = buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(onSubscribe)
            .doOnNext(onNext)
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe({}, Timber::w)
        disposables.add(disposable)
    }

    override fun dispose() {
        disposables.dispose()
        disposables = CompositeDisposable()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}