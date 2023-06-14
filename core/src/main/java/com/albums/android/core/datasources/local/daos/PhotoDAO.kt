package com.albums.android.core.datasources.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.albums.android.core.datasources.local.models.CachePhoto
import io.reactivex.Single

@Dao
interface PhotoDAO {

    @Query("SELECT * FROM photo WHERE albumId=:albumId")
    fun getByAlbumId(albumId: String): Single<List<CachePhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg photo: CachePhoto)

    @Query("DELETE FROM photo")
    fun clear()
}