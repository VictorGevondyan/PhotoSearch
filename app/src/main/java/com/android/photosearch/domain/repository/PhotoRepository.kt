package com.android.photosearch.domain.repository

import com.android.photosearch.domain.model.Photo
import io.reactivex.Observable


/**
 * To make an interaction between [PhotoRepositoryImp] & [GetPhotosUseCase]
 */
interface PhotoRepository {
    fun getPhotos(searchQuery: String): Observable<List<Photo>>
}