package com.kmozcan1.a20210327_ozcan_yooxtest.domain

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YooxApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.ProductRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.enumeration.ProductSortType
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.model.Product
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
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
class ProductListTest {
    private lateinit var repository: ProductRepository
    private lateinit var useCase: GetProductsUseCase
    private val mockResult = mockk<List<Product>>(relaxed = true)
    private val mockYooxApi = mockk<YooxApi>(relaxed = true)
    private val mockProductDataToDomainMapper = mockk<ProductDataToDomainMapper>(relaxed = true)
    private val mockProductDetailDataToDomainMapper = mockk<ProductDetailDataToDomainMapper>(relaxed = true)

    @Before
    fun setUp() {
        repository = ProductRepositoryImpl(
            mockYooxApi,
            mockProductDataToDomainMapper, mockProductDetailDataToDomainMapper
        )
        useCase = GetProductsUseCase(repository)
    }

    @Test
    fun `test GetProductsUseCase with default sort type`() {
        every { repository.getProductList(ProductSortType.DEFAULT) } returns Single.just(mockResult)

        val test = useCase.buildObservable(GetProductsUseCase.Params(
            ProductSortType.DEFAULT)).test()

        // verify that yooxApi.getProducts() is being called
        verify { mockYooxApi.getProducts() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mockResult)
    }

    @Test
    fun `test GetProductsUseCase with lowest sort type`() {
        every { repository.getProductList(ProductSortType.LOW_PRICE) } returns Single.just(mockResult)

        val test = useCase.buildObservable(GetProductsUseCase.Params(
            ProductSortType.LOW_PRICE)).test()

        // verify that yooxApi.getLowPriceProducts() is being called
        verify { mockYooxApi.getLowPriceProducts() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mockResult)
    }

    @Test
    fun `test GetProductsUseCase with highest sort type`() {
        every { repository.getProductList(ProductSortType.HIGH_PRICE) } returns
                Single.just(mockResult)

        val test = useCase.buildObservable(GetProductsUseCase.Params(
            ProductSortType.HIGH_PRICE)).test()

        // verify that yooxApi.getHighPriceProducts() is being called
        verify { mockYooxApi.getHighPriceProducts() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mockResult)
    }

    @Test
    fun `test GetProductsUseCase with latest sort type`() {
        every { repository.getProductList(ProductSortType.LATEST_ARRIVALS) } returns Single.just(mockResult)

        val test = useCase.buildObservable(GetProductsUseCase.Params(
            ProductSortType.LATEST_ARRIVALS)).test()

        // verify that yooxApi.getLatestProducts() is being called
        verify { mockYooxApi.getLatestProducts() }

        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(mockResult)
    }

    @Test
    fun `test if there are any errors when empty list is returned`() {
        every { repository.getProductList(ProductSortType.DEFAULT) }returns
                Single.just(emptyList())

        val test = useCase.buildObservable(GetProductsUseCase.Params(
            ProductSortType.DEFAULT)).test()

        // verify that yooxApi.getProducts() is being called
        verify { mockYooxApi.getProducts() }
        // assert onComplete event
        test.assertComplete()
        // assert that no errors were thrown
        test.assertNoErrors()
        // assert only one value was received
        test.assertValueCount(1)
        // assert the received object is equal to mocked result
        test.assertValue(emptyList())
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}