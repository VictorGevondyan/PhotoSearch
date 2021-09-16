package com.android.photosearch.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.photosearch.R
import com.android.photosearch.databinding.HolderPhotoBinding
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.presentation.search.SearchResultsAdapter.PhotoViewHolder
import java.util.*

/**
 * This class is responsible for converting each data entry [Photo]
 * into [PhotoViewHolder] that can then be added to the AdapterView.
 */
class SearchResultsAdapter(diffCallback: DiffUtil.ItemCallback<Photo>) :
    PagingDataAdapter<Photo, PhotoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val holderPhotoBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.holder_photo,
            parent,
            false
        )
        return PhotoViewHolder(holderPhotoBinding)

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class PhotoViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(photo: Photo) {
            val holderPhotoBinding = dataBinding as HolderPhotoBinding
            val photoViewModel = PhotoViewModel(photo)
            holderPhotoBinding.photoViewModel = photoViewModel
        }

    }

}
