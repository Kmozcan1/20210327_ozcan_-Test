package com.kmozcan1.a20210327_ozcan_yooxtest.domain

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.YooxDatabase
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDomainToDataMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.BrowsingHistoryRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.ProductRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetBrowsingHistoryListUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.UpdateBrowsingHistoryUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class BrowsingHistoryTest {

    private lateinit var repository: BrowsingHistoryRepository
    private lateinit var getBrowsingHistoryListUseCase: GetBrowsingHistoryListUseCase
    private lateinit var updateBrowsingHistoryUseCase: UpdateBrowsingHistoryUseCase
    private val mockResult = mockk<List<BrowsingHistoryEntity>>(relaxed = true)
    private val browsingHistoryDao = mockk<BrowsingHistoryDao>(relaxed = true)
    private val mockBrowsingHistoryToDomainMapper = mockk<BrowsingHistoryDataToDomainMapper>(relaxed = true)
    private val mockBrowsingHistoryToDataMapper = mockk<BrowsingHistoryDomainToDataMapper>(relaxed = true)

    @Before
    fun setUp() {
        repository = BrowsingHistoryRepositoryImpl(browsingHistoryDao,
            mockBrowsingHistoryToDomainMapper, mockBrowsingHistoryToDataMapper)
        getBrowsingHistoryListUseCase = GetBrowsingHistoryListUseCase(repository)
        updateBrowsingHistoryUseCase = UpdateBrowsingHistoryUseCase(repository)
    }

    @Test
    fun `test GetBrowsingHistoryListUseCase`() {
        every { browsingHistoryDao.getBrowsingHistoryList() } returns Single.just(mockResult)

        val test = getBrowsingHistoryListUseCase.buildObservable().test()

        // verify that browsingHistoryDao.getBrowsingHistoryList() is being called
        verify { browsingHistoryDao.getBrowsingHistoryList() }

        val mappedResult = mockBrowsingHistoryToDomainMapper.transform(mockResult)

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mappedResult)
    }

    @Test
    fun `test GetBrowsingHistoryListUseCase empty result`() {
        every { repository.getBrowsingHistoryList() } returns Single.just(emptyList())

        val test = getBrowsingHistoryListUseCase.buildObservable().test()

        // verify that browsingHistoryDao.getBrowsingHistoryList() is being called
        verify { browsingHistoryDao.getBrowsingHistoryList() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(emptyList())
    }

    @Test
    fun `test UpdateBrowsingHistoryUseCase`() {
        val mockBrowsingHistory = mockk<BrowsingHistory>(relaxed = true)
        val mappedBrowsingHistory = mockBrowsingHistoryToDataMapper.map(mockBrowsingHistory)
        every { browsingHistoryDao.insertBrowsingHistory(mappedBrowsingHistory) } returns
                Completable.complete()

        val test = updateBrowsingHistoryUseCase.buildObservable(UpdateBrowsingHistoryUseCase.Params(mockBrowsingHistory)).test()

        // verify that browsingHistoryDao.getBrowsingHistoryList() is being called
        verify { browsingHistoryDao.insertBrowsingHistory(mappedBrowsingHistory) }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}