package com.kmozcan1.a20210327_ozcan_yooxtest.domain

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.ProductRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.ProductDetail
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductDetailUseCase
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.usecase.GetProductsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 */
class ProductDetailTest {

    private lateinit var repository: ProductRepository
    private lateinit var useCase: GetProductDetailUseCase
    private val mockResult = mockk<ProductDetail>(relaxed = true)
    private val mockYooxApi = mockk<YooxApi>(relaxed = true)
    private val mockProductDataToDomainMapper = mockk<ProductDataToDomainMapper>(relaxed = true)
    private val mockProductDetailDataToDomainMapper = mockk<ProductDetailDataToDomainMapper>(relaxed = true)

    @Before
    fun setUp() {
        repository = ProductRepositoryImpl(
            mockYooxApi,
            mockProductDataToDomainMapper, mockProductDetailDataToDomainMapper
        )
        useCase = GetProductDetailUseCase(repository)
    }

    @Test
    fun `test GetProductDetailUseCase`() {
        every { repository.getProductDetail() } returns Single.just(mockResult)

        val test = useCase.buildObservable().test()

        // verify that yooxApi.getProducts() is being called
        verify { mockYooxApi.getProductDetail() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mockResult)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}