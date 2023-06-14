package com.albums.android.core.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class CacheAlbum(
    @PrimaryKey val id: String,
    val title: String,
    val userId: String
)