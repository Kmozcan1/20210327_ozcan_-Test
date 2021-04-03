package com.kmozcan1.a20210327_ozcan_yooxtest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetBrowsingHistoryListUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.BrowsingHistoryDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.mapper.ProductDomainToUiMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.BrowsingHistoryUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.BrowsingHistoryViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewmodel.ProductListViewModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.BrowsingHistoryViewState
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.viewstate.ProductListViewState
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Callable
import kotlin.test.assertEquals

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class BrowsingHistoryTest   {
    private lateinit var viewModel: BrowsingHistoryViewModel
    private val mockBrowsingHistoryDomainToUiMapper = mockk<BrowsingHistoryDomainToUiMapper>(relaxed = true)
    private val mockResult = mockk<List<BrowsingHistory>>(relaxed = true)
    private val mockObserver = mockk<Observer<BrowsingHistoryViewState>>(relaxed = true) { every { onChanged(any()) } just Runs }
    private val mockRepository = mockk<BrowsingHistoryRepository>(relaxed = true)
    private lateinit var useCase: GetBrowsingHistoryListUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
                scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        useCase = GetBrowsingHistoryListUseCase(mockRepository)
        viewModel = BrowsingHistoryViewModel(useCase, mockBrowsingHistoryDomainToUiMapper)
    }


    @Test
    fun `test getProductList`() {
        every { useCase.buildObservable() } returns Single.just(mockResult)

        viewModel.viewState.observeForever(mockObserver)

        viewModel.getBrowsingHistoryList()

        val mappedResult = mockBrowsingHistoryDomainToUiMapper.transform(mockResult)

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, BrowsingHistoryViewState.State.BROWSING_HISTORY_LIST_RESULT)

        assertEquals(viewModel.viewState.value!!.browsingHistoryList, mappedResult)
    }

    @Test
    fun `test getProductList empty list result`() {
        every { useCase.buildObservable() } returns Single.just(emptyList())

        viewModel.viewState.observeForever(mockObserver)

        viewModel.getBrowsingHistoryList()

        val mappedResult = mockBrowsingHistoryDomainToUiMapper.transform(emptyList())

        verify { mockObserver.onChanged(viewModel.viewState.value!!) }

        assertEquals(viewModel.viewState.value!!.state, BrowsingHistoryViewState.State.BROWSING_HISTORY_LIST_RESULT)

        assertEquals(viewModel.viewState.value!!.browsingHistoryList, mappedResult)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}