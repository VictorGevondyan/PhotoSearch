package com.android.photosearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.usecase.GetPhotosUseCase
import com.android.photosearch.util.LDEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
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

    private val _photosList = MutableLiveData<LDEvent<PagingData<Photo>>>()
    val photosList: LiveData<LDEvent<PagingData<Photo>>> = _photosList

    private val _isLoading = MutableLiveData<LDEvent<Boolean>>()
    val isLoading: LiveData<LDEvent<Boolean>> = _isLoading

    var previousQueryText = ""

    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        loadPhotos()
    }

    fun loadPhotos() {

//        _isLoading.value = LDEvent(true)
        getPhotoListUseCase.execute(
            params = GetPhotosUseCase.GetPhotosParams(
                searchSubject,
                20,
                0,
                previousQueryText
            )
        ).cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _photosList.value = LDEvent(pagingData)
            }, {
                it.printStackTrace()
            })

    }

    fun setSearch(queryText: String) {

        if (previousQueryText != queryText) {

            previousQueryText = queryText
//            _photosList.value = LDEvent(PagingData.empty()/*listOf()*/)
//            _isLoading.value = LDEvent(true)
//            searchSubject.onNext(queryText)
            loadPhotos()

        }

    }


}
