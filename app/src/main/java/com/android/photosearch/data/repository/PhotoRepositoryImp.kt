package com.android.photosearch.data.repository

import com.android.photosearch.data.source.remote.RetrofitService
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.repository.PhotoRepository
import io.reactivex.Observable

/**
 * This repository is responsible for
 * fetching data [photo] from server
 * */
class PhotoRepositoryImp(
    private val retrofitService: RetrofitService
) : PhotoRepository {

    override fun getPhotos(searchQuery: String): Observable<List<Photo>> {
        return retrofitService.getPhotos(searchQuery)
    }

}