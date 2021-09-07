package com.android.photosearch.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.photosearch.data.source.local.AppDatabase
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.util.TestUtil
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PhotoDaoTest {

    private lateinit var mDatabase: AppDatabase

    @Before
    fun createDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPhoto() {
        val photo: Photo = TestUtil.createPhoto(7)
        val insertedPhoto = mDatabase.photoDao.insert(photo)
        assertNotNull(insertedPhoto)
    }

    @Test
    @Throws(Exception::class)
    fun insertPhotoAndLoadByTitle() {

        val photo: Photo = TestUtil.createPhoto(1).apply {
            title = "Cat"
        }
        mDatabase.photoDao.insert(photo)

        val photoLoadedByTitleTestSingle = mDatabase.photoDao.getPhotoByText("Cat").test()
        photoLoadedByTitleTestSingle.assertValue(photo)

    }

    @Test
    @Throws(Exception::class)
    fun retrievesPhotos() {
        val photoList = TestUtil.makePhotoList(5)
        photoList.forEach {
            mDatabase.photoDao.insert(it)
        }

        val loadedPhotosTestSingle = mDatabase.photoDao.loadAll().test()
        loadedPhotosTestSingle.assertValue(photoList)
    }

    @Test
    @Throws(Exception::class)
    fun deletePhoto() {
        val photo = TestUtil.createPhoto(8)
        mDatabase.photoDao.delete(photo)

        val loadOneByPhotoIdTestSingle = mDatabase.photoDao.getPhoto(8).test()
        loadOneByPhotoIdTestSingle.assertNoValues()
    }

}