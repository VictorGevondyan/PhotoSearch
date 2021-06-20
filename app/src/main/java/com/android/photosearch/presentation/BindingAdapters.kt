package com.android.photosearch.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.photosearch.domain.model.Photo

@BindingAdapter("translation")
fun setTranslation(textView: TextView, photo: Photo) {
    textView.text = photo.meanings[0].translation.translationText
}

