package com.android.photosearch.domain.usecase.base

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

/**
 * This abstract class is shared among several closely related UseCase classes
 * that classes that extend this abstract class to use common methods & fields
 **/
abstract class FlowableUseCase<T, in Params> {

    private val compositeDisposable = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    fun execute(
//        onSuccess: (t: T) -> Unit,
//        onError: (t: Throwable) -> Unit,
//        onFinished: () -> Unit = {},
        params: Params
    ) : Flowable<T> {

        val flowable = buildUseCaseObservable(params)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())

//        addDisposable(flowable)

        return flowable
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.clear()
    }



}