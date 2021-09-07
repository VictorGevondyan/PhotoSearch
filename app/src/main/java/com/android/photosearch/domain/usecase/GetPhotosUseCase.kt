package com.android.photosearch.domain.usecase

import androidx.paging.PagingData
import com.android.photosearch.data.source.remote.model.PhotoSearchResponse
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.repository.PhotoRepository
import com.android.photosearch.domain.usecase.base.FlowableUseCase
import com.android.photosearch.domain.usecase.base.ObservableUseCase
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable.fromObservable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This class is an implementation of {@link ObservableUseCase} that represents a use case for
 * retrieving a collection of all {@link Photo}, defined by search.
 */
class GetPhotosUseCase @Inject constructor(private val repository: PhotoRepository) :
    FlowableUseCase<PagingData<Photo>, GetPhotosUseCase.GetPhotosParams>() {

    override fun buildUseCaseObservable(params: GetPhotosParams): Flowable<PagingData<Photo>> {

         /*params.searchSubject.debounce(300, TimeUnit.MILLISECONDS)
                .filter { queryText -> queryText.length > 1 }
                .distinctUntilChanged()
                .switchMap { queryText ->*/

        return  repository.getPhotos(
                        params.queryText,
                        params.photosPerPage
                    )
//                        .subscribeOn(Schedulers.io())
//                        .toObservable()
//
//                }
//            .toFlowable(BackpressureStrategy.LATEST)

    }

    data class GetPhotosParams(
        val searchSubject: PublishSubject<String>,
        val photosPerPage: Int,
        val currentPage: Int,
        val queryText:String
    )

}