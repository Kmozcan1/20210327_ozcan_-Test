package com.kmozcan1.a20210327_ozcan_yooxtest.application.di

import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YamlApi
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.BrowsingHistoryDomainToDataMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.mapper.ProductDetailDataToDomainMapper
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.BrowsingHistoryRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.data.repository.ProductRepositoryImpl
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.BrowsingHistoryRepository
import com.kmozcan1.a20210327_ozcan_yooxtest.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Dagger module for providing repository classes
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModel {
    @Provides
    fun provideProductRepository(
        yamlApi: YamlApi,
        productDataToDomainMapper: ProductDataToDomainMapper,
        productDetailDataToDomainMapper: ProductDetailDataToDomainMapper
    ): ProductRepository = ProductRepositoryImpl(yamlApi,
            productDataToDomainMapper, productDetailDataToDomainMapper)

    @Provides
    fun provideBrowsingHistoryRepository(
            browsingHistoryDao: BrowsingHistoryDao,
            browsingHistoryToDomainMapper: BrowsingHistoryDataToDomainMapper,
            browsingHistoryToDataMapper: BrowsingHistoryDomainToDataMapper
    ): BrowsingHistoryRepository = BrowsingHistoryRepositoryImpl(browsingHistoryDao,
            browsingHistoryToDomainMapper, browsingHistoryToDataMapper)
}