package com.kmozcan1.a20210327_ozcan_yooxtest.application.di

import android.app.Activity
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by Kadir Mert Özcan on 27-Mar-21.
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun providesMainActivity(
            activity: Activity
    ): MainActivity {
        return activity as MainActivity
    }
}