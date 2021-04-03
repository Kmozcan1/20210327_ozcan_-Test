package com.kmozcan1.a20210327_ozcan_yooxtest.application.di

import android.app.Application
import androidx.room.Room
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.YooxDatabase
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Dagger module for providing room database and data access objects
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideTrackDatabase(application: Application): YooxDatabase {
        return Room.databaseBuilder(
            application,
            YooxDatabase::class.java,
            YooxDatabase.DB_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    internal fun provideBrowsingHistoryDao(appDatabase: YooxDatabase): BrowsingHistoryDao {
        return appDatabase.browsingHistoryDao()
    }
}