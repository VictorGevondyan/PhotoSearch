package com.android.photosearch

import androidx.appcompat.app.AppCompatActivity
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.presentation.OnSearchActivityCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity(), OnSearchActivityCallback {
    override fun openPhotoDetail(photo: Photo) {}
}