package com.android.photosearch.presentation.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.photosearch.R
import com.android.photosearch.databinding.ActivitySearchBinding
import com.android.photosearch.presentation.base.BaseActivity
import com.android.photosearch.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding

    private var searchResultsAdapter: SearchResultsAdapter? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

//        setSupportActionBar(binding.activityToolbar)

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
    }

    override fun bindLiveData() {

        viewModel.isLoading.observe( this , {
            it.getIfNotHandled()?.let { isLoading ->
                binding.photosProgressBar.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        })

        viewModel.photosList.observe(this , {
            it.getIfNotHandled()?.let { photosList ->
                searchResultsAdapter?.addData(photosList)
            }
        })

    }

    private fun setupRecyclerView() {

        searchResultsAdapter = SearchResultsAdapter(this)
        binding.searchRecyclerView.adapter = searchResultsAdapter
        binding.searchRecyclerView.layoutManager =
            GridLayoutManager(this, Constants.GRID_SPAN_COUNT)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.searchRecyclerView.addItemDecoration(decoration)

    }

}
