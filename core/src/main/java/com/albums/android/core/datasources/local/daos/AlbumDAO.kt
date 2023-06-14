package com.albums.android.core.datasources.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.albums.android.core.datasources.local.models.CacheAlbum
import io.reactivex.Single

@Dao
interface AlbumDAO {

    @Query("SELECT * FROM album")
    fun getAll(): Single<List<CacheAlbum>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg albums: CacheAlbum)

    @Query("DELETE FROM album")
    fun clear()
}