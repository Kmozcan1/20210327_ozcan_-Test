package com.kmozcan1.a20210327_ozcan_yooxtest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.UpdateBrowsingHistoryUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.BrowsingHistoryUiToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDetailDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductDetailViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductDetailViewState
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Callable


/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class ProductDetailTest {
    private lateinit var viewModel: ProductDetailViewModel
    private val mockProductDetailDomainToUiMapper = mockk<ProductDetailDomainToUiMapper> (relaxed = true)
    private val mockBrowsingHistoryToDomainMapper = mockk<BrowsingHistoryUiToDomainMapper> (relaxed = true)
    private val mockResult = mockk<ProductDetail>(relaxed = true)
    private val mockObserver = mockk<Observer<ProductDetailViewState>>(relaxed = true) { every { onChanged(
        any()
    ) } just Runs }
    private val mockProductRepository = mockk<ProductRepository>(relaxed = true)
    private val mockBrowsingHistoryRepository = mockk<BrowsingHistoryRepository>(relaxed = true)
    private lateinit var updateBrowsingHistoryUseCase: UpdateBrowsingHistoryUseCase
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        updateBrowsingHistoryUseCase = UpdateBrowsingHistoryUseCase(mockBrowsingHistoryRepository)
        getProductDetailUseCase = GetProductDetailUseCase(mockProductRepository)
        viewModel = ProductDetailViewModel(
            getProductDetailUseCase,
            updateBrowsingHistoryUseCase,
            mockProductDetailDomainToUiMapper,
            mockBrowsingHistoryToDomainMapper
        )
    }


    @Test
    fun `test getProductDetail`() {
        every { getProductDetailUseCase.buildObservable() } returns Single.just(mockResult)

        viewModel.viewState.observeForever(mockObserver)

        viewModel.getProductDetail()

        val mappedResult = mockProductDetailDomainToUiMapper.map(mockResult)

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, ProductDetailViewState.State.PRODUCT_DETAIL_RESULT)

        assertEquals(viewModel.viewState.value!!.productDetail, mappedResult)

    }

    @Test
    fun `test updateBrowsingHistory`() {
        val mockBrowsingHistory = mockk<BrowsingHistoryUiModel>(relaxed = true)
        val mappedBrowsingHistory = mockBrowsingHistoryToDomainMapper.map(mockBrowsingHistory)
        every { updateBrowsingHistoryUseCase.buildObservable(UpdateBrowsingHistoryUseCase.Params(mappedBrowsingHistory)) } returns Completable.complete()

        viewModel.viewState.observeForever(mockObserver)

        viewModel.updateBrowsingHistory(mockBrowsingHistory)

        verify {mockBrowsingHistoryRepository.updateBrowsingHistory(mappedBrowsingHistory)}

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, ProductDetailViewState.State.BROWSING_HISTORY_UPDATED)

    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}