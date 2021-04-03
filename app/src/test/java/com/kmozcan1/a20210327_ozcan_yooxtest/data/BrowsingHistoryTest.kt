package com.kmozcan1.a20210327_ozcan_yooxtest.data

import android.content.Context
import androidx.room.Room
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.YooxDatabase
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDomainToDataMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.BrowsingHistoryRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.BrowsingHistory
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */

class BrowsingHistoryTest {

    private lateinit var repository: BrowsingHistoryRepository
    private val mockBrowsingHistoryDao = mockk<BrowsingHistoryDao>(relaxed = true)
    private val mockResult = mockk<List<BrowsingHistoryEntity>>(relaxed = true)
    private val mockBrowsingHistoryToDomainMapper = mockk<BrowsingHistoryDataToDomainMapper>(relaxed = true)
    private val mockBrowsingHistoryToDataMapper = mockk<BrowsingHistoryDomainToDataMapper>(relaxed = true)



    @Before
    fun setUp() {
        repository = BrowsingHistoryRepositoryImpl(mockBrowsingHistoryDao,
            mockBrowsingHistoryToDomainMapper, mockBrowsingHistoryToDataMapper)
    }

    @Test
    fun `test getBrowsingHistoryList`() {
        every { mockBrowsingHistoryDao.getBrowsingHistoryList() } returns Single.just(mockResult)

        val test = repository.getBrowsingHistoryList().test()

        verify { mockBrowsingHistoryDao.getBrowsingHistoryList() }

        val mappedResult = mockBrowsingHistoryToDomainMapper.transform(mockResult)

        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedResult)
    }

    @Test
    fun `test empty getBrowsingHistoryList result`() {
        every { mockBrowsingHistoryDao.getBrowsingHistoryList() } returns Single.just(emptyList())

        val test = repository.getBrowsingHistoryList().test()

        verify { mockBrowsingHistoryDao.getBrowsingHistoryList() }

        val mappedResult = mockBrowsingHistoryToDomainMapper.transform(emptyList())

        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedResult)
    }

    @Test
    fun `test updateBrowsingHistory`() {
        val mockBrowsingHistory = mockk<BrowsingHistory>(relaxed = true)
        val mappedBrowsingHistory = mockBrowsingHistoryToDataMapper.map(mockBrowsingHistory)
        every { mockBrowsingHistoryDao.insertBrowsingHistory(mappedBrowsingHistory) } returns
                Completable.complete()

        val test = repository.updateBrowsingHistory(mockBrowsingHistory).test()

        verify { mockBrowsingHistoryDao.insertBrowsingHistory(mappedBrowsingHistory) }

        test.assertComplete()
        test.assertNoErrors()
    }


    @After
    fun afterTests() {
        unmockkAll()
    }
}