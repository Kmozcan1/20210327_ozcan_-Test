package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setRecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductListViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<ProductListFragmentBinding, ProductListViewModel>() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override val layoutId: Int = R.layout.product_list_fragment

    override val viewModelClass: Class<ProductListViewModel> = ProductListViewModel::class.java

    // RecyclerView Adapter
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(mutableListOf())
    }

    override fun onViewBound() {
        setProductList()
    }

    private fun setProductList() {
        with(binding) {
            productListRecyclerView
                .setRecyclerView(GridLayoutManager(context, 2), productListAdapter)
            // will set callback listener
        }
    }

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    // Observes the ViewState LiveData
    private fun viewStateObserver() = Observer<ProductListViewState> { viewState ->
        when (viewState.state) {
            ERROR -> makeToast(viewState.errorMessage)
            INITIAL -> {
                // This is always false during the application launch, because the internet
                // because the fragment gets created before the connection status can update
                // But this if condition still is useful when the fragment is navigated to
                // from another
                if (getIsConnectedToInternet()) {
                    viewModel.getProducts()
                }
            }
            LOADING -> {
                // Show progress bar while waiting for api response
                binding.productListProgressBar.visibility = View.VISIBLE
            }
            LIST_RESULT -> {
                binding.productListProgressBar.visibility = View.GONE
                viewState.productList?.let { productListResult ->
                    if(productListResult.isEmpty()) {
                        // list doesn't return empty with the current api call,
                        // can be useful is actual search is implemented
                    } else {
                        // add the list to the RecyclerView
                        productListAdapter.addProductList(productListResult)
                    }
                }
            }
        }
    }

    override fun onInternetDisconnected() {
        binding.productListProgressBar.visibility = View.GONE
        // Show empty warning only if the list hasn't been loaded yet
        if (productListAdapter.productList.isEmpty()) {
            binding.connectivityWarningTextView.visibility = View.VISIBLE
        }
        super.onInternetDisconnected()
    }

    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.connectivityWarningTextView.visibility = View.GONE
        }
        if (previouslyDisconnected && productListAdapter.productList.isEmpty()) {
            viewModel.getProducts()
        }
        super.onInternetConnected()
    }

}