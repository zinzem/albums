package com.albums.android.core.datasources.jsonplaceholder

import com.albums.android.core.BuildConfig
import com.albums.android.core.datasources.jsonplaceholder.daos.AlbumDAO
import com.albums.android.core.network.HttpClient
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JsonPlaceholderDataSource(httpClient: HttpClient) {

    val albumDAO: AlbumDAO by lazy {  retrofit.create(AlbumDAO::class.java) }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(httpClient.client)
        .build()
}