package com.albums.android.core.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.albums.android.core.datasources.local.daos.AlbumDAO
import com.albums.android.core.datasources.local.daos.PhotoDAO
import com.albums.android.core.datasources.local.models.CacheAlbum
import com.albums.android.core.datasources.local.models.CachePhoto

@Database(entities = [CacheAlbum::class, CachePhoto::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun albums(): AlbumDAO
    abstract fun photos(): PhotoDAO

    override fun clearAllTables() {
        albums().clear()
    }
}

class LocalDataSource(context: Context) {

    val localDB: LocalDatabase by lazy {
        Room.databaseBuilder(context, LocalDatabase::class.java, "localDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}
