package com.milad.moviesdb.data.local

import androidx.room.TypeConverter

class IntArrayTypeConverter {
    @TypeConverter
    fun fromSource(array: List<Int>): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toSource(array: String): List<Int> {
        if (array.isNullOrEmpty()) {
            return ArrayList()
        } else {
            var r: ArrayList<Int> = ArrayList()
            array.split(",").forEach {
                r.add(it.toInt())
            }
            return r
        }
    }
}