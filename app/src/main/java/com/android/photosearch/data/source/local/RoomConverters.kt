package com.android.photosearch.data.source.local

import androidx.room.TypeConverter
import com.android.photosearch.domain.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RoomConverters {

    @TypeConverter
    fun convertMeaningArr(list: ArrayList<Photo.Meaning?>?): String? {

        return if (list == null) {
            null
        } else {
            Gson().toJson(list)
        }

    }

    @TypeConverter
    fun toMeaningArr(string: String?): ArrayList<Photo.Meaning?>? {

        if (string == null) {
            return null
        }

        val gson = Gson()

        val type =
            object : TypeToken<List<Photo.Meaning?>?>() {}.type

        return gson.fromJson<ArrayList<Photo.Meaning?>>(
            string,
            type
        )

    }

}