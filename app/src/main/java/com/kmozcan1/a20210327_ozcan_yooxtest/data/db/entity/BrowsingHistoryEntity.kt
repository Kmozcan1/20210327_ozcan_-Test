package com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity.Companion.BRAND
import com.kmozcan1.a20210327_ozcan_yooxtest.data.db.entity.BrowsingHistoryEntity.Companion.CODE10
import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Entity object for browsing history table entries
 */
@Entity(tableName = BrowsingHistoryEntity.TABLE_NAME, primaryKeys = [CODE10])
data class BrowsingHistoryEntity(
        @ColumnInfo(name = CODE10)
        var code10: String,
        @ColumnInfo(name = BRAND)
        var brand: String,
        @ColumnInfo(name = CATEGORY)
        var category: String,
        @ColumnInfo(name = IMAGE_URL)
        var imageUrl: String,
        @ColumnInfo(name = VISIT_DATETIME)
        var visitDateTime: Date
){
    companion object{
        const val TABLE_NAME = "browsing_history"
        const val CODE10 = "code10"
        const val BRAND = "brand"
        const val CATEGORY = "category"
        const val IMAGE_URL = "image_url"
        const val VISIT_DATETIME = "visit_datetime"
    }
}