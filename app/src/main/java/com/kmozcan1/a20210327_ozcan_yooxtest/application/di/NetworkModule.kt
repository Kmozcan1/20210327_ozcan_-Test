package com.kmozcan1.a20210327_ozcan_yooxtest.application.di

import com.kmozcan1.a20210327_ozcan_yooxtest.BuildConfig
import com.kmozcan1.a20210327_ozcan_yooxtest.data.api.YamlApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Kadir Mert Özcan on 29-Mar-21.
 *
 * Dagger module for providing network related objects
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private var url = HttpUrl.Builder()
            .scheme("https")
            .host("5aaf9b98bcad130014eeaf0b.mockapi.io")
            .addPathSegment("ynap")
            .addPathSegment("v1")
            .addPathSegment("") // to add "/" at the end
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }else{
        OkHttpClient
                .Builder()
                .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun providesRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
            converterFactory: Converter.Factory,
            callAdapterFactory: RxJava3CallAdapterFactory,
            okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideYamlApi(
        retrofit: Retrofit
    ): YamlApi {
        return retrofit.create(YamlApi::class.java)
    }

}