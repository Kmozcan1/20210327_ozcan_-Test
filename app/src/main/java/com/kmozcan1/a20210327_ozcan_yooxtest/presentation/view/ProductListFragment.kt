package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType.*
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
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

    // To prevent clicks to sort buttons while repositories are still loading
    var sortDisabled = true

    override val layoutId: Int = R.layout.product_list_fragment

    override val viewModelClass: Class<ProductListViewModel> = ProductListViewModel::class.java

    // RecyclerView Adapter
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(mutableListOf()) { product ->
            onProductListItemClick(product)
        }
    }

    // Product sort bottom sheet fragment instance, inflated when the sort button is tapped
    private val productSortBottomSheetFragment: ProductSortBottomSheetFragment by lazy {
        ProductSortBottomSheetFragment()
    }

    override fun onViewBound() {
        // Used for calling button click methods from xml
        binding.productListFragment = this

        // Sets up the RecyclerView adapter
        setProductList()
    }

    private fun setProductList() {
        binding.productListRecyclerView
                .setRecyclerView(GridLayoutManager(context, 2), productListAdapter)

    }

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    /** Observes the ViewState LiveData */
    private fun viewStateObserver() = Observer<ProductListViewState> { viewState ->
        when (viewState.state) {
            ERROR -> makeToast(viewState.errorMessage)
            INITIAL -> {
                if(productListAdapter.productList.isEmpty()) {
                    binding.productListProgressBar.visibility = View.VISIBLE
                }

                // This is always false during the application launch,
                // because the fragment gets created before the connection status can update
                // But this if condition still is useful when the fragment is navigated to
                // from another fragment
                if (getIsConnectedToInternet()) {
                    viewModel.getProducts(DEFAULT)
                }
            }
            LOADING -> {
                handleLoadingState(isLoading = true)
            }
            LIST_RESULT -> {
                handleLoadingState(isLoading = false)
                viewState.productList?.let { productListResult ->
                    if(productListResult.isEmpty()) {
                        // list doesn't return empty with the current api call,
                        // can be useful is actual search is implemented
                    } else {
                        // When coming from product detail or history fragment, update sort button
                        viewModel.run {
                            getHasRetainedListState()?.let { hasRetainedList ->
                                if (hasRetainedList) {
                                    getSortButtonState()?.let { productSortType ->
                                        updateSortButtonText(productSortType)
                                    }
                                }
                            }
                        }
                        // add the list to the RecyclerView
                        productListAdapter.addProductList(productListResult)
                    }
                }
            }
        }
    }

    /** Shows/hides the progress bar and enables/disables the sort button
     * based on the product list loading status*/
    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.productListProgressBar.visibility = View.VISIBLE
            sortDisabled = true
        } else {
            binding.productListProgressBar.visibility = View.GONE
            sortDisabled = false
        }
    }

    /** Called when the sortProductsButton is used, referenced in the fragment's xml file*/
    fun onSortButtonClick(v: View) {
        if (!sortDisabled) {
            // disable sort button to prevent double click
            sortDisabled = true
            productSortBottomSheetFragment.arguments = Bundle().apply {
                productSortBottomSheetFragment.show(childFragmentManager, tag)
            }
        }
    }

    /** Called when a button inside the [ProductSortBottomSheetFragment] is used
     * Calls the ViewModel method the observe the list of products based on the sort type
     * Also changes the sort button text*/
    fun onBottomSheetButtonClick(productSortType: ProductSortType) {
        // enable sort button
        sortDisabled = false

        // close the bottom sheet fragment
        productSortBottomSheetFragment.dismiss()

        // clears the previous list
        productListAdapter.clearProductList()

        // scrolls to the top of the product ist
        productListAdapter.scrollToTop()

        // update the button text
        updateSortButtonText(productSortType)

        // call viewModel method to observe the list
        viewModel.getProducts(productSortType)
    }

    private fun updateSortButtonText(productSortType: ProductSortType) {
        binding.sortProductsButton.text = when (productSortType) {
            DEFAULT ->
                getString(R.string.sort_products)
            LATEST_ARRIVALS ->
                getString(R.string.sort_latest_arrivals)
            LOW_PRICE ->
                getString(R.string.sort_low_price)
            HIGH_PRICE ->
                getString(R.string.sort_high_price)
        }
    }

    /** Called when an item from product list is clicked
     * Added parameters will be used for updating browsing history */
    private fun onProductListItemClick(product: ProductUiModel) {
        // So that fragment knows that it already has a list after coming back
        viewModel.setHasRetainedListState(true)

        val navAction: NavDirections
        product.run {
            navAction = ProductListFragmentDirections
                    .actionProductListFragmentToProductDetailFragment(code10, brand, category, imageUrl)
        }
        navController.navigate(navAction)
    }

    /** Called when the historyButton is clicked, navigates to BrowsingHistoryFragment */
    fun onHistoryButtonClick(v: View) {
        // So that fragment knows that it already has a list after coming back
        viewModel.setHasRetainedListState(true)

        val navAction = ProductListFragmentDirections
            .actionProductListFragmentToBrowsingHistoryFragment()
        navController.navigate(navAction)
    }

    /** Invoked when the internet is disconnected */
    override fun onInternetDisconnected() {
        binding.productListProgressBar.visibility = View.GONE
        // Show connection warning only if the list hasn't been loaded yet
        if (productListAdapter.productList.isEmpty()) {
            binding.productListConnectivityWarningTextView.visibility = View.VISIBLE
        }
        super.onInternetDisconnected()
    }

    /** Invoked when the internet is connected */
    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.productListConnectivityWarningTextView.visibility = View.GONE
        }
        if (previouslyDisconnected && productListAdapter.productList.isEmpty()) {
            viewModel.getProducts(DEFAULT)
        }
        super.onInternetConnected()
    }

    override fun onResume() {
        sortDisabled = false
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productListAdapter.clearProductList()
    }
}