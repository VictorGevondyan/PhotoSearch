package com.android.photosearch.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.photosearch.data.source.local.dao.PhotoDao
import com.android.photosearch.domain.model.Photo

/**
 * To manage data items that can be accessed, updated
 * & maintain relationships between them
 */
@Database(entities = [Photo::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val photoDao: PhotoDao

    companion object {
        const val DB_NAME = "PhotoSearchDatabase.db"
    }

}
