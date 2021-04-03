package com.kmozcan1.a20210327_ozcan_yooxtest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductListViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class ProductListTest {
    private lateinit var viewModel: ProductListViewModel
    private val mockProductDomainToUiMapper = mockk<ProductDomainToUiMapper>(relaxed = true)
    private val mockSavedStateHandle = mockk<SavedStateHandle>(relaxed = true)
    private val mockResult = mockk<List<Product>>(relaxed = false)
    private val mockObserver = mockk<Observer<ProductListViewState>>(relaxed = true) { every { onChanged(
        any()
    ) } just Runs }
    private val mockRepository = mockk<ProductRepository>(relaxed = true)
    private lateinit var useCase: GetProductsUseCase


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        useCase = GetProductsUseCase(mockRepository)
        viewModel = ProductListViewModel(
            mockSavedStateHandle, useCase,
            mockProductDomainToUiMapper
        )
    }


    @Test
    fun `test getProductList`() {
        every { useCase.buildObservable(GetProductsUseCase.Params(ProductSortType.DEFAULT)) } returns Single.just(mockResult)

        viewModel.viewState.observeForever(mockObserver)

        viewModel.getProducts(ProductSortType.DEFAULT)

        val mappedResult = mockProductDomainToUiMapper.transform(mockResult)

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, ProductListViewState.State.LIST_RESULT)

        assertEquals(viewModel.viewState.value!!.productList, mappedResult)

    }

    @Test
    fun `test getProductList empty list result`() {
        every { useCase.buildObservable(GetProductsUseCase.Params(ProductSortType.DEFAULT)) } returns
                Single.just(emptyList())

        viewModel.viewState.observeForever(mockObserver)

        viewModel.getProducts(ProductSortType.DEFAULT)

        val mappedResult = mockProductDomainToUiMapper.transform(emptyList())

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, ProductListViewState.State.LIST_RESULT)

        assertEquals(viewModel.viewState.value!!.productList, mappedResult)

    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}