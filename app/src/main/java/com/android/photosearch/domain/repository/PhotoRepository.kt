package com.android.photosearch.domain.repository

import androidx.paging.PagingData
import com.android.photosearch.data.source.remote.model.PhotoSearchResponse
import com.android.photosearch.domain.model.Photo
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * To make an interaction between [PhotoRepositoryImp] & [GetPhotosUseCase]
 */
interface PhotoRepository {

    fun getPhotos(
        searchQuery: String,
        photosPerPage: Int
    ): Flowable<PagingData<Photo>>

}