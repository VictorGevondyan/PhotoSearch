package com.android.photosearch.domain.usecase

import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.repository.PhotoRepository
import com.android.photosearch.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This class is an implementation of {@link ObservableUseCase} that represents a use case for
 * retrieving a collection of all {@link Photo}, defined by search.
 */
class GetPhotosUseCase @Inject constructor(private val repository: PhotoRepository) :
    ObservableUseCase<List<Photo>, GetPhotosUseCase.GetPhotosParams>() {

    override fun buildUseCaseObservable(params: GetPhotosParams): Observable<List<Photo>> {

        return params.searchSubject.debounce(300, TimeUnit.MILLISECONDS)
            .filter { queryText -> queryText.length > 1 }
            .distinctUntilChanged()
            .switchMap { queryText -> repository.getPhotos(queryText) }
            .observeOn(Schedulers.io())

    }

    data class GetPhotosParams(
        val searchSubject: PublishSubject<String>
    )

}