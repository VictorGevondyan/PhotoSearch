package com.android.photosearch.presentation

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.android.photosearch.data.source.remote.FlickrService
import com.android.photosearch.domain.model.Photo
import com.bumptech.glide.Glide

@BindingAdapter("photo")
fun setPhoto(imageView: AppCompatImageView, photo: Photo) {
    Glide.with(imageView.context)
        .load(FlickrService.getPhotoURL(photo))
        .override(500)
        .centerCrop()
        .into(imageView)

}

