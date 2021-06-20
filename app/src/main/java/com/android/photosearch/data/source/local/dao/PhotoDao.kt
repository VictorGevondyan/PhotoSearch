package com.android.photosearch.data.source.local.dao


import androidx.room.*
import com.android.photosearch.domain.model.Photo
import io.reactivex.Single

/**
 * it provides access to [Photo] underlying database
 * */
@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo): Long

    @Query("SELECT * FROM Photo")
    fun loadAll(): Single<MutableList<Photo>?>

    @Delete
    fun delete(photo: Photo)

    @Query("DELETE FROM Photo")
    fun deleteAll()

    @Query("SELECT * FROM Photo where id = :id")
    fun getPhoto(id: Long): Single<Photo?>

    @Query("SELECT * FROM Photo where text = :photoTitle")
    fun getPhotoByText(photoTitle: String): Single<Photo?>

    @Update
    fun update(photo: Photo)

}