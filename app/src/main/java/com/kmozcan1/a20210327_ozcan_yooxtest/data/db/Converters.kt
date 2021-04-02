package com.kmozcan1.a20210327_ozcan_yooxtest.data.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 */
class Converters {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return value.let { Date(it) }
    }
}
