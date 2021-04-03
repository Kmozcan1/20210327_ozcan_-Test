package com.kmozcan1.a20210327_ozcan_yooxtest.data

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductDetailResponse
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
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class ProductDetailTest {
    private val fileReader = FileReader()
    val json = fileReader.readFileWithoutNewLineFromResources("item.json")
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private lateinit var repository: ProductRepository
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
    fun `test if api call response is converted correctly to ProductDetailResponse`() {
        val adapter: JsonAdapter<ProductDetailResponse> =
            moshi.adapter(ProductDetailResponse::class.java)
        val resultObject = adapter.fromJson(json)
        assertNotNull(resultObject)
        assertEquals(3, resultObject!!.colors.size)
        every { mockYooxApi.getProductDetail() } returns Single.just(resultObject)
        val test = repository.getProductDetail().test()
        val mappedProducts = mockProductDetailDataToDomainMapper.map(resultObject)
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