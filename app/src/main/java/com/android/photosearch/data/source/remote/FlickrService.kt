package com.android.photosearch.data.source.remote

import android.net.Uri
import com.android.photosearch.domain.model.Photo

object FlickrService {
    // String to create Flickr API urls
    const val FLICKR_BASE_URL = "http://api.flickr.com/services/rest/?method="
    const val FLICKR_GET_PHOTO_BASE_URL = "https://live.staticflickr.com/"
    const val FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search"
    private const val FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes"
    private const val FLICKR_PHOTOS_SEARCH_ID = 1
    private const val FLICKR_GET_SIZES_ID = 2
    private const val NUMBER_OF_PHOTOS = 20

    //You can set here your API_KEY
    // TODO: SET THE KEY
    const val APIKEY_SEARCH_STRING = "&api_key=248933e95b1159a747c5ff9c17a1fb26"
    private const val TEXT_STRING = "&text="
    const val FORMAT_STRING = "&format=json"
    const val NO_JSON_STRING = "&nojsoncallback=1"
    private const val PER_PAGE_STRING = "&per_page="
    private const val PHOTO_ID_STRING = "&photo_id="
    const val PHOTO_THUMB = 111
    const val PHOTO_LARGE = 222

    //    public static UIHandler uihandler;
    private fun createURL(methodId: Int, parameter: String): String? {

        var methodType = ""
        var url: String? = null
        when (methodId) {

            FLICKR_PHOTOS_SEARCH_ID -> {
//                methodType = FLICKR_PHOTOS_SEARCH_STRING

                // TODO: Think about removing "media"
                url =
                    "$FLICKR_BASE_URL$FLICKR_PHOTOS_SEARCH_STRING$APIKEY_SEARCH_STRING" +
                            "$FORMAT_STRING$NO_JSON_STRING"
            }

            FLICKR_GET_SIZES_ID -> {
                methodType = FLICKR_GET_SIZES_STRING
                url =
                    FLICKR_BASE_URL + methodType + PHOTO_ID_STRING + parameter + APIKEY_SEARCH_STRING + FORMAT_STRING
            }

        }

        return url

    }

    fun getPhotoURL(photo: Photo): Uri {
        val urlString =
            FLICKR_GET_PHOTO_BASE_URL + photo.server + "/" + photo.id + "_" + photo.secret + ".jpg"
        return Uri.parse(urlString)
    }

}