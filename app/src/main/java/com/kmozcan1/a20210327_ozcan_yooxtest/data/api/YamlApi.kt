package com.kmozcan1.a20210327_ozcan_yooxtest.data.api

import com.kmozcan1.a20210327_ozcan_yooxtest.data.apimodel.ProductApiModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Retrofit endpoint for Api calls to the Yaml test Api
 */
@JvmSuppressWildcards
interface YamlApi  {

    /** Request method for the "/searchresult/ */
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("searchresult")
    fun getProducts(): Single<List<ProductApiModel>>
}