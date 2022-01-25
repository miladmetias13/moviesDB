package com.milad.moviesdb.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milad.moviesdb.model.entity.Genre.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
data class Genre(

    @PrimaryKey
    var id: Int? = 0,
    var name: String? = null,
    var updatedAt : Long? = Calendar.getInstance().time.time

) {
    companion object {
        const val TABLE_NAME = "Genres"
    }
}

