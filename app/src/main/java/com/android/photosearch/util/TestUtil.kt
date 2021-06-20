package com.android.photosearch.util

import com.android.photosearch.domain.model.Photo

object TestUtil {

    fun createPhoto(id: Long) = Photo(
        id = id,
        text = "",
        meanings = arrayListOf(
            Photo.Meaning(
                0L,
                Photo.Meaning.Translation(
                    "",
                    ""
                ),
                "",
                "",
                ""
            )
        )
    )

    fun makePhotoList(size: Int): MutableList<Photo> {
        val list = ArrayList<Photo>(size)
        list.forEach {
            it.text = "Photo ${list.indexOf(it)}"
            it.id = (list.indexOf(it) + 1).toLong()
            list.add(it)
        }
        return list
    }
}