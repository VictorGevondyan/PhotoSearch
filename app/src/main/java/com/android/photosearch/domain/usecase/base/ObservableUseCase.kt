package com.android.photosearch.domain.usecase.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This abstract class is shared among several closely related UseCase classes
 * that classes that extend this abstract class to use common methods & fields
 **/
abstract class ObservableUseCase<T, in Params> : UseCase() {

    abstract fun buildUseCaseObservable(params: Params): Single<T>

    fun execute(
        onSuccess: (t: T) -> Unit,
        onError: (t: Throwable) -> Unit,
        onFinished: () -> Unit = {},
        params: Params
    ) {

        disposeLast()
        lastDisposable = buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            addDisposable(it)
        }

    }

}