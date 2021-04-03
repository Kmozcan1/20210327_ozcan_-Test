package com.kmozcan1.a20210327_ozcan_yooxtest.data

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.SearchResultResponse
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.ProductRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.data.util.FileReader
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class ProductListTest {
    private val fileReader = FileReader()
    val json = fileReader.readFileWithoutNewLineFromResources("searchresult.json")
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private lateinit var repository: ProductRepository
    val mockResult = mockk<SearchResultResponse>(relaxed = true)
    private val mockYooxApi = mockk<YooxApi>(relaxed = true)
    private val mockProductDataToDomainMapper = mockk<ProductDataToDomainMapper>(relaxed = true)
    private val mockProductDetailDataToDomainMapper = mockk<ProductDetailDataToDomainMapper>(relaxed = true)

    @Before
    fun setUp() {
        repository = ProductRepositoryImpl(
            mockYooxApi,
            mockProductDataToDomainMapper, mockProductDetailDataToDomainMapper
        )
    }

    @Test
    fun `test if api call response is converted correctly to SearchResultResponse`() {
        val adapter: JsonAdapter<SearchResultResponse> =
            moshi.adapter(SearchResultResponse::class.java)
        val resultObject = adapter.fromJson(json)
        assertNotNull(resultObject)

        // check number of products
        assertEquals(40, resultObject!!.products.size)
        every { mockYooxApi.getProducts() } returns
                Single.just(SearchResultResponse(resultObject.products))
        val test = repository.getProductList(ProductSortType.DEFAULT).test()
        val mappedProducts = mockProductDataToDomainMapper.transform(resultObject.products)

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mappedProducts (to test the mapper)
        test.assertValue(mappedProducts)
    }

    @Test
    fun `test searchresult call`() {
        every { mockYooxApi.getProducts() } returns
                Single.just(SearchResultResponse(mockResult.products))
        val test = repository.getProductList(ProductSortType.DEFAULT).test()

        // verify that getProduct method was called (after repository.getProductList)
        verify { mockYooxApi.getProducts() }

        val mappedProducts = mockProductDataToDomainMapper.transform(mockResult.products)
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedProducts)
    }

    @Test
    fun `test latest call`() {
        every { mockYooxApi.getLatestProducts() } returns
                Single.just(SearchResultResponse(mockResult.products))
        val test = repository.getProductList(ProductSortType.LATEST_ARRIVALS).test()

        verify { mockYooxApi.getLatestProducts() }

        val mappedProducts = mockProductDataToDomainMapper.transform(mockResult.products)
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedProducts)
    }

    @Test
    fun `test lowest call`() {
        every { mockYooxApi.getLowPriceProducts() } returns
                Single.just(SearchResultResponse(mockResult.products))
        val test = repository.getProductList(ProductSortType.LOW_PRICE).test()

        verify { mockYooxApi.getLowPriceProducts() }

        val mappedProducts = mockProductDataToDomainMapper.transform(mockResult.products)
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedProducts)
    }

    @Test
    fun `test highest call`() {
        every { mockYooxApi.getHighPriceProducts() } returns
                Single.just(SearchResultResponse(mockResult.products))
        val test = repository.getProductList(ProductSortType.HIGH_PRICE).test()
        verify { mockYooxApi.getHighPriceProducts() }
        val mappedProducts = mockProductDataToDomainMapper.transform(mockResult.products)
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedProducts)
    }

    @Test
    fun `test if repository and mapper throw error if empty list is returned by search result`() {
        every { mockYooxApi.getProducts() } returns
                Single.just(SearchResultResponse(products = listOf()))
        val test = repository.getProductList(ProductSortType.DEFAULT).test()
        verify { mockYooxApi.getProducts() }
        val mappedProducts = mockProductDataToDomainMapper.transform(
            SearchResultResponse(products = listOf()).products)
        test.assertComplete()
        test.assertNoErrors()
        test.assertValueCount(1)
        test.assertValue(mappedProducts)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}