package com.albums.android.core.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo",
    foreignKeys = [ForeignKey(
        entity = CacheAlbum::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("albumId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class CachePhoto(
    @PrimaryKey val id: String,
    @ColumnInfo(index = true)  val albumId: String,
    val title: String,
    val url: String
)