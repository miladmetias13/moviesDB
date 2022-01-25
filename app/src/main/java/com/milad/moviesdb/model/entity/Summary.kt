package com.milad.moviesdb.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milad.moviesdb.model.entity.Summary.Companion.TABLE_NAME
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = TABLE_NAME)
data class Summary(

    @PrimaryKey
    var id: Int? = 0,
    var poster_path: String? = null,
    var adult: Boolean?= null,
    var overview: String?= null,
    var release_date: String?= null,
    var genre_ids: List<Int> = ArrayList(),
    var original_title: String?= null,
    var original_language: String?= null,
    var title: String?= null,
    var backdrop_path: String?= null,
    var popularity: Float?= null,
    var vote_count: Int?= null,
    var video: Boolean?= null,
    var vote_average: Float?= null,
    var page: Int?,
    var updatedAt : Long? = Calendar.getInstance().time.time
) {

    fun getPoster():String
    {
        return IMAGE_URL + poster_path
    }

    companion object {
        const val TABLE_NAME = "Summaries"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
    }
}
