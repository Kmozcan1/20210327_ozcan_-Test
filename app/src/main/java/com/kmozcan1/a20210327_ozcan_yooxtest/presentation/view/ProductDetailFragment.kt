package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayoutMediator
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductDetailFragmentBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ColorButtonListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.ProductImagePagerAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter.SizeButtonListAdapter
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ColorVariantUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductDetailUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setRecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductDetailViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState.State.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<ProductDetailFragmentBinding, ProductDetailViewModel>() {

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    override val layoutId: Int = R.layout.product_detail_fragment

    override val viewModelClass: Class<ProductDetailViewModel> = ProductDetailViewModel::class.java

    private var browsingHistoryUpdated: Boolean = false

    private var productDetailsFetched: Boolean = false

    private val args: ProductDetailFragmentArgs by navArgs()

    // RecyclerView Adapter for product images
    private val productImagePagerAdapter: ProductImagePagerAdapter by lazy {
        ProductImagePagerAdapter(mutableListOf())
    }

    // Flexbox Layout used for color button recycler view
    private val colorButtonListFlexboxLayoutManager: FlexboxLayoutManager by lazy {
        FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            flexWrap = FlexWrap.WRAP
        }
    }

    // Flexbox Layout used for size button recycler view
    private val sizeButtonListFlexboxLayoutManager: FlexboxLayoutManager by lazy {
        FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            flexWrap = FlexWrap.WRAP
        }
    }

    // RecyclerView Adapter for color button list
    private val colorButtonCallbackListener = colorButtonCallbackListener()
    private val colorButtonListAdapter: ColorButtonListAdapter by lazy {
        ColorButtonListAdapter(mutableListOf(), colorButtonCallbackListener)
    }

    // RecyclerView Adapter for size button list
    private val sizeButtonCallbackListener = sizeButtonCallbackListener()
    private val sizeButtonListAdapter: SizeButtonListAdapter by lazy {
        SizeButtonListAdapter(mutableListOf(), sizeButtonCallbackListener)
    }


    override fun onViewBound() {
        setProductImageViewPager()
        setSizeButtonList()
        setColorButtonList()
    }

    private fun setProductImageViewPager() {
        binding.productImageViewPager.adapter = productImagePagerAdapter
        val productImageTabLayout = binding.productImageTabLayout
        TabLayoutMediator(productImageTabLayout, binding.productImageViewPager) { _, _ -> }.attach()
    }

    private fun setColorButtonList() {
        binding.colorButtonListRecyclerView
                .setRecyclerView(colorButtonListFlexboxLayoutManager, colorButtonListAdapter)
    }

    private fun setSizeButtonList() {
        binding.sizeButtonListRecyclerView
                .setRecyclerView(sizeButtonListFlexboxLayoutManager, sizeButtonListAdapter)
    }

    override fun observeLiveData() {
        // Observes the ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    /** Observes the ViewState LiveData */
    private fun viewStateObserver() = Observer<ProductDetailViewState> { viewState ->
        when (viewState.state) {
            ERROR -> makeToast(viewState.errorMessage) // for debugging purposes
            INITIAL -> {
                args.run {
                    if (!getIsConnectedToInternet()) {
                        binding.productDetailConnectivityWarningTextView.visibility = View.VISIBLE
                        handleLoadingState(isLoading = false)
                    } else {
                        handleLoadingState(isLoading = true)
                    }
                    viewModel.updateBrowsingHistory(
                            BrowsingHistoryUiModel(
                                    code10, productBrand, productCategory,
                                    productImageUrl, Date(System.currentTimeMillis())
                            )
                    )
                }
            }
            LOADING -> {
                handleLoadingState(isLoading = true)
            }
            BROWSING_HISTORY_UPDATED -> {
                browsingHistoryUpdated = true
                if (getIsConnectedToInternet()) {
                    viewModel.getProductDetail()
                }
            }
            PRODUCT_DETAIL_RESULT -> {
                productDetailsFetched = true
                viewState.productDetail?.let { productDetailResult ->
                    setViews(productDetailResult)
                }
            }
        }
    }

    private fun setViews(productDetailResult: ProductDetailUiModel) {
        binding.run {
            // set the TextViews
            brandNameDetailTextView.text = productDetailResult.brand
            categoryDetailTextView.text = productDetailResult.category
            priceDetailTextView.text = productDetailResult.price
            productInfoTextView.text = productDetailResult.productInfo
        }

        // add sizes to the size button recycler view adapter
        sizeButtonListAdapter.addSizeList(productDetailResult.sizeList)

        // add colors to the color button recycler view adapter and performs initial click
        colorButtonListAdapter.apply {
            addColorList(productDetailResult.colorVariantList)
        }
        // refresh size buttons for the first color
        sizeButtonListAdapter
                .refreshButtons(productDetailResult.colorVariantList[0].availableSizeList)
    }

    /** Returns color button callback listener object */
    private fun colorButtonCallbackListener() = object : ColorButtonListAdapter.CallbackListener {
        override fun onColorButtonClick(colorVariant: ColorVariantUiModel) {
            productImagePagerAdapter.apply {
                clearImageUrlList()
                addImageUrlList(colorVariant.imgUrlList)
            }
            sizeButtonListAdapter.apply {
                unSelectButton()
                refreshButtons(colorVariant.availableSizeList)
            }
            // Set color label
            binding.colorDetailTextView.text =
                    context?.getString(R.string.color_name, colorVariant.name)
            handleLoadingState(false)
        }
    }

    /** Returns size button callback listener object */
    private fun sizeButtonCallbackListener() = object : SizeButtonListAdapter.CallbackListener {
        override fun onSizeButtonClick() {
            // no implementation needed at the moment
        }
    }

    /** Shows/hides the progress bar and view components */
    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading && getIsConnectedToInternet()) {
            binding.productDetailProgressBar.visibility = View.VISIBLE
            binding.productDetailContainer.visibility = View.INVISIBLE
        } else {
            binding.productDetailProgressBar.visibility = View.GONE
            if (getIsConnectedToInternet()) {
                binding.productDetailContainer.visibility = View.VISIBLE

            }
        }
    }

    /** Invoked when the internet is disconnected */
    override fun onInternetDisconnected() {
        handleLoadingState(false)
        // Show connection warning only if the product details haven't been loaded yet
        if (!productDetailsFetched) {
            binding.productDetailConnectivityWarningTextView.visibility = View.VISIBLE
        }
        super.onInternetDisconnected()
    }

    /** Invoked when the internet is connected */
    override fun onInternetConnected() {
        if (previouslyDisconnected) {
            binding.productDetailConnectivityWarningTextView.visibility = View.GONE
        }
        if (previouslyDisconnected && browsingHistoryUpdated && !productDetailsFetched) {
            viewModel.getProductDetail()
        }
        super.onInternetConnected()
    }
}