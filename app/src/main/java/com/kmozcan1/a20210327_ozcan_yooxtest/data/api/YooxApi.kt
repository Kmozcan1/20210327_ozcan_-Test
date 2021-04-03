package com.kmozcan1.a20210327_ozcan_yooxtest.data.api

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductDetailResponse
import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.SearchResultResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Retrofit endpoint for Api calls to the Yoox test Api
 */
@JvmSuppressWildcards
interface YooxApi  {

    /** Request method for the "/searchresult" */
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("searchresult")
    fun getProducts(): Single<SearchResultResponse>

    /** Request method for the "/latest" */
    @Headers(
            "Accept: application/vnd.github.v3+json"
    )
    @GET("latest")
    fun getLatestProducts(): Single<SearchResultResponse>

    /** Request method for the "/lowest" */
    @Headers(
            "Accept: application/vnd.github.v3+json"
    )
    @GET("lowest")
    fun getLowPriceProducts(): Single<SearchResultResponse>

    /** Request method for the "/highest" */
    @Headers(
            "Accept: application/vnd.github.v3+json"
    )
    @GET("highest")
    fun getHighPriceProducts(): Single<SearchResultResponse>

    /** Request method for the "/item" */
    @Headers(
            "Accept: application/vnd.github.v3+json"
    )
    @GET("item")
    fun getProductDetail(): Single<ProductDetailResponse>
}