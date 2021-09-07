package com.android.photosearch.presentation.search

import androidx.recyclerview.widget.DiffUtil
import com.android.photosearch.domain.model.Photo

object PhotoComparator: DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

}