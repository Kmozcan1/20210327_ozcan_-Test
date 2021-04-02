package com.kmozcan1.a20210327_ozcan_yooxtest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.YooxDatabase.Companion.DB_VERSION
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Room database object
 */
@Database(entities = [BrowsingHistoryEntity::class], version = DB_VERSION)
abstract class YooxDatabase : RoomDatabase() {
    abstract fun browsingHistoryDao(): BrowsingHistoryDao

    companion object {
        const val DB_VERSION = 1

        const val DB_NAME = "yoox.db"

        @Volatile
        private var dbInstance: YooxDatabase? = null

        fun getDatabaseInstance(mContext: Context): YooxDatabase =
            dbInstance ?: synchronized(this) {
                dbInstance ?: buildDatabaseInstance(mContext).also {
                    dbInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, YooxDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }

}

