package com.kmozcan1.a20210327_ozcan_yooxtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Room data access object for browsing history table
 */
@Dao
interface BrowsingHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBrowsingHistory(entity: BrowsingHistoryEntity) : Completable

    @Query("SELECT * FROM ${BrowsingHistoryEntity.TABLE_NAME} ORDER BY visit_datetime DESC" )
    fun getBrowsingHistory(): Single<List<BrowsingHistoryEntity>>

}