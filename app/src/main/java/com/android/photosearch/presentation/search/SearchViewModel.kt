package com.android.photosearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.android.photosearch.data.source.remote.PhotoPagingSource
import com.android.photosearch.data.source.remote.RetrofitService
import com.android.photosearch.domain.model.Photo
import com.android.photosearch.domain.usecase.GetPhotosUseCase
import com.android.photosearch.util.LDEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


/**
 * To store & manage UI-related data in a lifecycle conscious way.
 * This class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetPhotosUseCase]
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getPhotoListUseCase: GetPhotosUseCase
//    private val retrofitService: RetrofitService
) :
    ViewModel() {

    private val _photosList = MutableLiveData<LDEvent<PagingData<Photo>>>()
    val photosList: LiveData<LDEvent<PagingData<Photo>>> = _photosList

    private val _isLoading = MutableLiveData<LDEvent<Boolean>>()
    val isLoading: LiveData<LDEvent<Boolean>> = _isLoading

    private var previousQueryText = ""
//    private var pageNumber = 0

    var flowable: Flowable<PagingData<Photo>>? = null

    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        loadPhotos()
//        val query = ""
//        val pager: Pager<Int, Photo> = Pager(
//            PagingConfig(pageSize = 20),
//            pagingSourceFactory = {
//                PhotoPagingSource(retrofitService, query)
//            }
//        )
//
//        flowable = pager.flowable
//        flowable.cachedIn(viewModelScope);

    }

    fun loadPhotos() : Flowable<PagingData<Photo>> {

//        _isLoading.value = LDEvent(true)
        return getPhotoListUseCase.execute(
            params = GetPhotosUseCase.GetPhotosParams(
                searchSubject,
                20,
                0,
                previousQueryText
            )
        ).cachedIn(viewModelScope)

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
