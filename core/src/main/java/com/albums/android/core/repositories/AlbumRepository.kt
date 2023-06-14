package com.albums.android.core.repositories

import com.albums.android.core.datasources.jsonplaceholder.JsonPlaceholderDataSource
import com.albums.android.core.datasources.local.LocalDataSource
import com.albums.android.core.models.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class AlbumRepository(
    private val localDataSource: LocalDataSource,
    private val jsonPlaceholderDataSource: JsonPlaceholderDataSource
) {

    fun getAlbums(forceRefresh: Boolean): Single<List<Album>> {
        return if (forceRefresh) getAlbumsFromNetwork() else getAlbumsFromCache()
    }

    fun getPhotos(albumId: String): Single<List<Photo>> {
        return getPhotosFromCache(albumId)
    }

    private fun getAlbumsFromCache(): Single<List<Album>> {
        return localDataSource.localDB.albums().getAll()
            .subscribeOn(Schedulers.io())
            .map { it.ifEmpty { throw Exception("no Album in cache") } }
            .map { it.map { cacheAlbum ->  cacheAlbum.toAlbum() } }
            .onErrorResumeNext(getAlbumsFromNetwork())
    }

    private fun getPhotosFromCache(albumId: String): Single<List<Photo>> {
        return localDataSource.localDB.photos().getByAlbumId(albumId)
            .subscribeOn(Schedulers.io())
            .map { it.ifEmpty { throw Exception("no Photo in cache") } }
            .map { it.map { cachePhoto ->  cachePhoto.toPhoto() } }
            .onErrorResumeNext(getPhotosFromNetwork(albumId))
    }

    private fun getAlbumsFromNetwork(): Single<List<Album>> {
        return jsonPlaceholderDataSource.albumDAO.getAlbums()
            .doOnSuccess { apiAlbums ->
                val cacheAlbums = apiAlbums.map { it.toCacheAlbum() }
                with(localDataSource.localDB.albums()) {
                    clear()
                    add(*cacheAlbums.toTypedArray())
                }
            }.map { it.map { apiAlbum -> apiAlbum.toAlbum() } }
    }

    private fun getPhotosFromNetwork(albumId: String): Single<List<Photo>> {
        return jsonPlaceholderDataSource.albumDAO.getAlbumPhotos(albumId)
            .doOnSuccess { apiPhotos ->
                val cachePhotos = apiPhotos.map { it.toCachePhoto() }
                with(localDataSource.localDB.photos()) {
                    clear()
                    add(*cachePhotos.toTypedArray())
                }
            }.map { it.map { apiPhoto -> apiPhoto.toPhoto() } }
    }
}