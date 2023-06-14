package com.albums.android.core.models

import com.albums.android.core.datasources.jsonplaceholder.models.ApiAlbum
import com.albums.android.core.datasources.local.models.CacheAlbum

data class Album(
    val id: String,
    val title: String,
    val userId: String
)

fun ApiAlbum.toAlbum() = Album(id, title, userId)
fun CacheAlbum.toAlbum() = Album(id, title, userId)
fun ApiAlbum.toCacheAlbum() = CacheAlbum(id, title, userId)