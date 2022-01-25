package com.milad.moviesdb.data.local

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun fromSource(date: Date): String {
        return date.time.toString()
    }

    @TypeConverter
    fun toSource(date: String): Date {
        return Date(date.toLong())
    }
}