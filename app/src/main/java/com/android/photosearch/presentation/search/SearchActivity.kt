package com.android.photosearch.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
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

        viewModel.photosList.observe(this, {
            it.getIfNotHandled()?.let { pagingData ->
                searchResultsAdapter?.submitData(lifecycle,pagingData)
            }
        })


    }

    private fun setupRecyclerView() {

        searchResultsAdapter = SearchResultsAdapter(PhotoComparator)
        binding.searchRecyclerView.adapter = searchResultsAdapter
        binding.searchRecyclerView.layoutManager =
            GridLayoutManager(this, Constants.GRID_SPAN_COUNT)

        searchResultsAdapter!!.addLoadStateListener { combinedLoadStates: CombinedLoadStates ->

//            combinedLoadStates.source.
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = combinedLoadStates.source.append as? LoadState.Error
                ?: combinedLoadStates.source.prepend as? LoadState.Error
                ?: combinedLoadStates.append as? LoadState.Error
                ?: combinedLoadStates.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
//        mDisposable.add(viewModel.loadPhotos().subscribe { pagingData ->
//            searchResultsAdapter?.submitData(lifecycle, pagingData)
//        })

    }

}
