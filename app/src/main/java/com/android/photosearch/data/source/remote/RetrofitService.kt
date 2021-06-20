package com.android.photosearch.data.source.remote

import com.android.photosearch.domain.model.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("photos/search")
    fun getPhotos(@Query("search") search: String): Observable<List<Photo>>

}