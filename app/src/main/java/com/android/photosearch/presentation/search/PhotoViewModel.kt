package com.android.photosearch.presentation.search

import androidx.lifecycle.MutableLiveData
import com.android.photosearch.domain.model.Photo

/**
 * A helper class for the UI controller that is responsible for
 * preparing data for [PhotoViewModel] as the UI
 * */
class PhotoViewModel(photo: Photo) {

    val photo = MutableLiveData<Photo>()

    init {
        this.photo.value = photo
    }

}