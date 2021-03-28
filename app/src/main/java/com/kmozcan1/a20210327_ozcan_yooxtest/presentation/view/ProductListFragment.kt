package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.view.View
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<ProductListFragmentBinding, ProductListViewModel>() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override val layoutId: Int = R.layout.product_list_fragment

    //override val viewModelClass: Class<ProductListViewModel> = ProductListViewModel::class.java


    override fun onViewBound() {
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