package com.mwdch.newsfeed.data

import com.google.gson.annotations.SerializedName

data class Response(
    val currentPage: Int,
    val orderBy: String,
    val pageSize: Int,
    val pages: Int,
    val startIndex: Int,
    val status: String,
    val total: Int,
    val userTier: String,
    @SerializedName("results")
    val news: List<News>
)