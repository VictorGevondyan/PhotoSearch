package com.android.photosearch.data.source.remote

import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.android.photosearch.data.source.remote.model.PhotoSearchResponse
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.util.Constants
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class PhotoPagingSource(
    private val retrofitService: RetrofitService,
    private var query: String
) : RxPagingSource<Int, Photo>() {

    override fun loadSingle(
        params: LoadParams<Int>
    ): Single<LoadResult<Int, Photo>> {

        // Start refresh at page 1 if undefined.
        var nextPageNumber = params.key
        if (nextPageNumber == null) {
            nextPageNumber = 1
        }

        return retrofitService.getPhotos(query, Constants.PHOTO_PAGE_SIZE,  nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map { response: PhotoSearchResponse -> toLoadResult(response, nextPageNumber) }
            .onErrorReturn { LoadResult.Error(it) }

    }

    private fun toLoadResult(
         response: PhotoSearchResponse,
         position:Int
    ): LoadResult<Int, Photo> {

        return LoadResult.Page(
            response.photosGeneralResponse.photoList,
            null,  // Only paging forward.
            position + 1,
            COUNT_UNDEFINED,
            COUNT_UNDEFINED
        )

    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        val anchorPosition = state.anchorPosition ?: return null
        val (_, prevKey, nextKey) = state.closestPageToPosition(anchorPosition)
            ?: return null
        if (prevKey != null) {
            return prevKey + 1
        }
        return if (nextKey != null) {
            nextKey - 1
        } else null
    }

}