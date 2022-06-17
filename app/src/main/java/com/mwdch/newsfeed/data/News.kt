package com.mwdch.newsfeed.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tbl_news")
@Parcelize
data class News(
//    val apiUrl: String,
    @PrimaryKey
    val id: String,
//    val isHosted: Boolean,
//    val pillarId: String,
//    val pillarName: String,
//    val sectionId: String,
//    val sectionName: String,
//    val webPublicationDate: String,
    val webUrl: String,
    @SerializedName("type")
    val category: String,
    @SerializedName("webTitle")
    val title: String,

    var isFavorite: Boolean = false
) : Parcelable