package com.mwdch.newsfeed.services.http

import com.mwdch.newsfeed.data.NewsResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory

interface ApiService {

    @GET("search")
    fun getAllNews(
        @Query("api-key") key: String,
        @Query("page") page: Int
    ): Flow<NewsResponse>

}

fun createApiServiceInstance(): ApiService {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://content.guardianapis.com/")
        .addCallAdapterFactory(FlowCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}