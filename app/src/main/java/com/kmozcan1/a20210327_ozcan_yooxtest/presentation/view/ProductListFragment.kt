package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setRecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<ProductListFragmentBinding, ProductListViewModel>() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override val layoutId: Int = R.layout.product_list_fragment

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
    }

    override fun onInternetDisconnected() {
        binding.connectionTest.visibility = View.VISIBLE
        super.onInternetDisconnected()
    }

    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.connectionTest.visibility = View.GONE
        }
        super.onInternetConnected()
    }

}