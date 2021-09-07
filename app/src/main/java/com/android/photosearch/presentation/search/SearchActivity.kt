package com.android.photosearch.presentation.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.android.photosearch.R
import com.android.photosearch.databinding.ActivitySearchBinding
import com.android.photosearch.presentation.base.BaseActivity
import com.android.photosearch.util.Constants
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding

//    private lateinit var  callback: Paginate.Callbacks

    private val mDisposable = CompositeDisposable()

    private var searchResultsAdapter: SearchResultsAdapter? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        val items = arrayListOf<String>()

        binding.searchViewModel = viewModel

        binding.searchView.setOnQueryTextListener(

            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(textToSubmit: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(queryText: String?): Boolean {
                    queryText?.let {
                        val parsedQuery = queryText.trim { it <= ' ' }
                        if (parsedQuery.length > 1) {
                            viewModel.setSearch(parsedQuery)
                        }
                    }
                    return true
                }

            }

        )

        val searchViewPlate =
            binding.searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)

        searchViewPlate.background.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(this, R.color.colorAccent),
                BlendModeCompat.SRC_ATOP
            )

        setupRecyclerView()
//        initPaginateCallback()

    }

    override fun onDestroy() {
        super.onDestroy()
        searchResultsAdapter = null
        mDisposable.dispose()
    }

    @ExperimentalCoroutinesApi
    override fun bindLiveData() {

        viewModel.isLoading.observe(this, {
            it.getIfNotHandled()?.let { isLoading ->
                binding.photosProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.dividerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        })

/*
        viewModel.setPagination.observe(this, {
            it.getIfNotHandled()?.let { setPagination ->
                if (setPagination) {
                    Paginate.with(binding.searchRecyclerView, callback)
                        .setLoadingTriggerThreshold(17)
                        .addLoadingListItem(true)
                        .setLoadingListItemSpanSizeLookup { 2 }
                        .build()
                }
            }
        })
*/

//        viewModel.photosList.observe(this, {
//            it.getIfNotHandled()?.let { pagingData ->
//                searchResultsAdapter?.submitData(lifecycle,pagingData)
//            }
//        })

//        mDisposable.add(viewModel.loadPhotos().subscribe { pagingData ->
//            searchResultsAdapter?.submitData(lifecycle,pagingData)
//        })
    }

    private fun setupRecyclerView() {

        searchResultsAdapter = SearchResultsAdapter(PhotoComparator)
        binding.searchRecyclerView.adapter = searchResultsAdapter
        binding.searchRecyclerView.layoutManager =
            GridLayoutManager(this, Constants.GRID_SPAN_COUNT)

        viewModel.flowable
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(  { pagingData -> searchResultsAdapter?.submitData(lifecycle, pagingData)},{})

    }

/*
    private fun initPaginateCallback() {

        callback = object : Paginate.Callbacks {
            override fun onLoadMore() {
                viewModel.loadPhotos()
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading.value?.getContent() ?: false // TODO: think about this
            }

            override fun hasLoadedAllItems(): Boolean {
                return viewModel.hasLoadedAllItems.value?.getContent() ?: false
            }
        }

    }
*/


}
