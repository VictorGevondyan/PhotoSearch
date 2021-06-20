package com.android.photosearch.domain.usecase.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * This class is extended by SingleUseCase classes
 * to use common methods & fields
 **/
abstract class UseCase {

    protected var lastDisposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    protected fun disposeLast() {

        lastDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.clear()
    }

}