package com.android.photosearch.data.source.remote.model

import com.android.photosearch.domain.model.Photo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotoSearchResponse(
    @SerializedName("photos")
    val photosGeneralResponse: PhotosGeneralResponse,
    @SerializedName("stat")
    val status: String
) : Serializable {

    data class PhotosGeneralResponse(
        @SerializedName("page")
        val currentPage: Int,
        @SerializedName("pages")
        val pagesCount: String,
        @SerializedName("perpage")
        val photosPerPage: Int,
        @SerializedName("total")
        val totalPagesCount: String,
        @SerializedName("photo")
        val photoList: ArrayList<Photo>,
    ) : Serializable

}
