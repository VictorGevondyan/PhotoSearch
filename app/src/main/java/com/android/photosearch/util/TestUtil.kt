package com.android.photosearch.util

import com.android.photosearch.domain.model.Photo

object TestUtil {

    fun createPhoto(id: Long) = Photo(id, "", "", "", "", "", "", "", "")


    fun makePhotoList(size: Int): MutableList<Photo> {
        val list = ArrayList<Photo>(size)
        list.forEach {
            it.id = (list.indexOf(it) + 1).toLong()
            it.title = "Photo ${list.indexOf(it)}"
            list.add(it)
        }
        return list
    }
}