package com.kmozcan1.a20210327_ozcan_yooxtest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.YooxDatabase
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao.BrowsingHistoryDao
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

/**
 * Created by Kadir Mert Özcan on 03-Apr-21.
 *
 * Test write & read to BrowsingHistoryDao table
 */
@RunWith(AndroidJUnit4::class)
class YooxDatabaseReadWriteTest {
    private lateinit var browsingHistoryDao: BrowsingHistoryDao
    private lateinit var db: YooxDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, YooxDatabase::class.java).build()
        browsingHistoryDao = db.browsingHistoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadTest() {
        val browsingHistoryEntity = BrowsingHistoryEntity(
            "12345", "brand",
            "category", "imageurl" , Date(System.currentTimeMillis()))
        browsingHistoryDao.insertBrowsingHistory(browsingHistoryEntity).blockingAwait()
        browsingHistoryDao.getBrowsingHistoryList().test().assertValue { list ->
            list.first() == browsingHistoryEntity && list.first().code10 == "12345"
        }
    }
}
