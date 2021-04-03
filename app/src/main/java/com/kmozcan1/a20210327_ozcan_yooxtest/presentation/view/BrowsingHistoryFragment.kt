package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.BrowsingHistoryFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.BrowsingHistoryListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setRecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.BrowsingHistoryViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.BrowsingHistoryViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.BrowsingHistoryViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowsingHistoryFragment :
        BaseFragment<BrowsingHistoryFragmentBinding, BrowsingHistoryViewModel>() {

    companion object {
        fun newInstance() = BrowsingHistoryFragment()
    }

    override val layoutId = R.layout.browsing_history_fragment

    override val viewModelClass = BrowsingHistoryViewModel::class.java

    private var browsingListRetrieved = false

    // RecyclerView Adapter
    private val browsingHistoryListAdapter: BrowsingHistoryListAdapter by lazy {
        BrowsingHistoryListAdapter(mutableListOf()) { browsingHistory ->
            onBrowsingHistoryListItemClick(browsingHistory)
        }
    }

    override fun onViewBound() {
        setBrowsingHistoryList()
    }

    private fun setBrowsingHistoryList() {
        binding.browsingHistoryRecyclerView
                .setRecyclerView(LinearLayoutManager(context), browsingHistoryListAdapter)
    }

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    private fun viewStateObserver() = Observer<BrowsingHistoryViewState> { viewState ->
        when (viewState.state) {
            ERROR -> makeToast(viewState.errorMessage)
            INITIAL -> {
                handleLoadingState(isLoading = true)
                if (getIsConnectedToInternet()) {
                    viewModel.getBrowsingHistoryList()
                }
            }
            LOADING -> {
                handleLoadingState(isLoading = true)
            }
            BROWSING_HISTORY_LIST_RESULT -> {
                // If already has the list from before, refresh it
                if (browsingListRetrieved) {
                    browsingListRetrieved = false
                    viewModel.getBrowsingHistoryList()
                } else {
                    browsingListRetrieved = true
                    viewState.browsingHistoryList?.let { browsingHistoryListResult ->
                        if(browsingHistoryListResult.isEmpty()) {
                            binding.browsingHistoryEmptyListTextView.visibility = View.VISIBLE
                        } else {
                            // add the list to the RecyclerView
                            browsingHistoryListAdapter.addBrowsingHistoryList(browsingHistoryListResult)
                        }
                    }
                    handleLoadingState(isLoading = false)
                }

            }
        }
    }

    /** Shows/hides the progress bar based on the browsing history list loading status */
    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.browsingHistoryProgressBar.visibility = View.VISIBLE
        } else {
            binding.browsingHistoryProgressBar.visibility = View.GONE
        }
    }

    /** Called when an item from browsing history list is clicked */
    private fun onBrowsingHistoryListItemClick(browsingHistory: BrowsingHistoryUiModel) {
        val navAction = BrowsingHistoryFragmentDirections
                .actionBrowsingHistoryFragmentToProductDetailFragment(browsingHistory.code10,
                        browsingHistory.brand, browsingHistory.category, browsingHistory.imageUrl)
        navController.navigate(navAction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        browsingHistoryListAdapter.clearBrowsingHistoryList()
    }

}