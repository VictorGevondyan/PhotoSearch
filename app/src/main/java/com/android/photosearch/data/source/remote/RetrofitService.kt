package com.android.photosearch.data.source.remote

import com.android.photosearch.data.source.remote.model.PhotoSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET(
        "${FlickrService.FLICKR_BASE_URL}${FlickrService.FLICKR_PHOTOS_SEARCH_STRING}" +
                "${FlickrService.APIKEY_SEARCH_STRING}${FlickrService.FORMAT_STRING}" +
                FlickrService.NO_JSON_STRING
    )
    fun getPhotos(
        @Query("text") searchText: String,
        @Query("per_page") photosPerPage: Int,
        @Query("page") currentPage: Int
    ): Single<PhotoSearchResponse>

}