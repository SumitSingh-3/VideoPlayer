package com.example.datalib.data.model;

import androidx.room.TypeConverter;
import java.util.*

object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long {
        return date?.time ?: 0
    }
}