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

    // Returns ViewState Observer object
    private fun viewStateObserver() = Observer<ProductListViewState> { viewState ->
        when (viewState.state) {
            ERROR -> makeToast(viewState.errorMessage)
            INITIAL -> if (getIsConnectedToInternet()) {
                viewModel.getProducts()
            }
            LOADING -> {/*loading stuff*/}
            LIST_RESULT -> {
                with(binding.productListRecyclerView) {
                    viewState.productList?.let { productListResult ->
                        if(productListResult.isEmpty()) {
                            //handle empty result
                        } else {
                            productListAdapter.addProductList(productListResult)
                        }
                    }
                }
            }
        }
    }

    override fun onInternetDisconnected() {
        binding.connectionTest.visibility = View.VISIBLE
        super.onInternetDisconnected()
    }

    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.connectionTest.visibility = View.GONE
        }
        if (previouslyDisconnected && productListAdapter.productList.isEmpty()) {
            viewModel.getProducts()
        }
        super.onInternetConnected()
    }

}