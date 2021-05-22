package com.example.mad03_fragments_and_navigation.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.mad03_fragments_and_navigation.R
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "my_watchlist")
data class Movie(
    @ColumnInfo(name = "title")
    var title: String = "",
    @Ignore
    var description: String = ""
): Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = 0L
    @Ignore
    var rating: Float = 0.0F
        set(value) {
            if(value in 0.0..5.0) field = value
            else throw IllegalArgumentException("Rating value must be between 0 and 5")
        }
    @ColumnInfo(name = "note")
    var note: String = ""
    @Ignore
    var imageId: Int = R.drawable.no_preview_3
    @Ignore
    var actors: MutableList<String> = mutableListOf()
    @Ignore
    var creators: MutableList<String> = mutableListOf()
    @Ignore
    var genres: List<String>? = null
}
