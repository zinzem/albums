package com.albums.android.core.datasources.jsonplaceholder.daos

import com.albums.android.core.datasources.jsonplaceholder.models.ApiAlbum
import com.albums.android.core.datasources.jsonplaceholder.models.ApiPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumDAO {

    @GET("albums")
    fun getAlbums(): Single<List<ApiAlbum>>

    @GET("albums/{id}/photos")
    fun getAlbumPhotos(@Path("id") id: String): Single<List<ApiPhoto>>
}