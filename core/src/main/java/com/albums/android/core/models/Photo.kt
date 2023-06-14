package com.albums.android.core.models

import com.albums.android.core.datasources.jsonplaceholder.models.ApiPhoto
import com.albums.android.core.datasources.local.models.CachePhoto

data class Photo(
    val id: String,
    val albumId: String,
    val title: String,
    val url: String
)

fun ApiPhoto.toPhoto() = Photo(id, albumId, title, url)
fun CachePhoto.toPhoto() = Photo(id, albumId, title, url)
fun ApiPhoto.toCachePhoto() = CachePhoto(id, albumId, title, url)
