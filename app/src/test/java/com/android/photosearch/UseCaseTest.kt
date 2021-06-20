package com.android.photosearch

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.photosearch.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ObservableUseCaseTest {

    private lateinit var useCase: ObservableUseCaseTestClass

    @Before
    fun setUp() {
        useCase = ObservableUseCaseTestClass()
    }

    @Test
    fun testBuildObservableUseCaseReturnCorrectResult() {

        useCase.execute(
            onSuccess = {
                useCase.valuesCount++
            },
            onError = {},
            onFinished = {},
            Params.EMPTY
        )

        Assert.assertEquals(0, useCase.valuesCount)

    }

    @Test
    fun testSubscriptionWhenExecutingObservableUseCase() {

        useCase.execute(
            onSuccess = {},
            onError = {},
            onFinished = {},
            Params.EMPTY
        )
        useCase.disposeLastDisposable()
        useCase.getDisposable()?.isDisposed?.let { Assert.assertTrue(it) }

    }

    private class ObservableUseCaseTestClass :
        ObservableUseCase<Any, Params>() {

        var valuesCount = 0

        override fun buildUseCaseObservable(params: Params): Observable<Any> {
            return Observable.empty()
        }

        fun getDisposable(): Disposable? {
            return lastDisposable
        }

        fun disposeLastDisposable() {
            super.disposeLast()
        }

    }

    private class Params {
        companion object {
            val EMPTY: Params = Params()
        }
    }

}