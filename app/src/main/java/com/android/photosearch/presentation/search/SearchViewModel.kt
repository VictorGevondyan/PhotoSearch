package com.android.photosearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.usecase.GetPhotosUseCase
import com.android.photosearch.util.LDEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * To store & manage UI-related data in a lifecycle conscious way.
 * This class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetPhotosUseCase]
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val getPhotoListUseCase: GetPhotosUseCase) :
    ViewModel() {

    private val _photosList = MutableLiveData<LDEvent<List<Photo>>>()
    val photosList: LiveData<LDEvent<List<Photo>>> = _photosList

    private val _isLoading = MutableLiveData<LDEvent<Boolean>>()
    val isLoading: LiveData<LDEvent<Boolean>> = _isLoading

    var previousQueryText = ""

    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        loadPhotos()
    }

    private fun loadPhotos() {

        getPhotoListUseCase.execute(
            onSuccess = {
                _isLoading.value = LDEvent(false)
                _photosList.value = LDEvent(it)
            },
            onError = {
                _isLoading.value = LDEvent(false)
                it.printStackTrace()
            },
            params = GetPhotosUseCase.GetPhotosParams(
                searchSubject
            )
        )

    }

    fun setSearch(queryText: String) {

        if (previousQueryText != queryText) {

            previousQueryText = queryText
            _photosList.value = LDEvent(listOf())
            _isLoading.value = LDEvent(true)
            searchSubject.onNext(queryText)

        }

    }

}