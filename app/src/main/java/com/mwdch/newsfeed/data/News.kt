package com.mwdch.newsfeed.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_news")
@Parcelize
data class News(
    @PrimaryKey
    val id: String,
    val webUrl: String,
    @SerializedName("type")
    val category: String,
    @SerializedName("webTitle")
    val title: String,
    var isFavorite: Boolean = false
) : Parcelable