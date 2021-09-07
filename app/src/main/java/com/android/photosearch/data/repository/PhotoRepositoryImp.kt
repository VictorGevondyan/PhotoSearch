package com.android.photosearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import androidx.paging.rxjava2.observable
import com.android.photosearch.data.source.remote.PhotoPagingSource
import com.android.photosearch.data.source.remote.RetrofitService
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.repository.PhotoRepository
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * This repository is responsible for
 * fetching data [photo] from server
 * */

class PhotoRepositoryImp @Inject constructor(
//    private val pagingSource: PhotoPagingSource
private val retrofitService: RetrofitService
) : PhotoRepository {

    @ExperimentalCoroutinesApi
    override fun getPhotos(
        searchQuery: String,
        photosPerPage: Int
//        ,
//        currentPage: Int
    ): Flowable<PagingData<Photo>> {

        val pagingSource = PhotoPagingSource(retrofitService, searchQuery)
//        pagingSource.setQuery(searchQuery)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable

    }

}