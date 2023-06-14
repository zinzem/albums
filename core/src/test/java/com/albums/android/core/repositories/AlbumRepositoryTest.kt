package com.albums.android.core.repositories

import com.albums.android.core.datasources.jsonplaceholder.JsonPlaceholderDataSource
import com.albums.android.core.datasources.jsonplaceholder.models.ApiAlbum
import com.albums.android.core.datasources.jsonplaceholder.models.ApiPhoto
import com.albums.android.core.datasources.local.LocalDataSource
import com.albums.android.core.datasources.local.models.CacheAlbum
import com.albums.android.core.datasources.local.models.CachePhoto
import com.albums.android.core.models.Album
import com.albums.android.core.models.Photo
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import io.reactivex.schedulers.Schedulers
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import java.lang.Exception

class AlbumRepositoryTest {

    private lateinit var subject: AlbumRepository

    private val api: JsonPlaceholderDataSource = mockk(relaxed = true)
    private val localDb: LocalDataSource = mockk(relaxed = true)

    private val cacheAlbum = CacheAlbum("cachealbum", "title", "def")
    private val apiAlbum = ApiAlbum("apialbum", "title", "def")
    private val album = Album("album", "title", "def")
    private val cachePhoto = CachePhoto("cachephoto", "abc", "title", "http://test.com")
    private val apiPhoto = ApiPhoto("apiphoto", "abc", "title", "http://test.com")
    private val photo = Photo("photo", "abc", "title", "http://test.com")

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        subject = AlbumRepository(localDb, api)
    }

    @Test
    fun getAlbumFromNetworkTest() {
        every { api.albumDAO.getAlbums() } returns Single.just(listOf(apiAlbum))

        subject.getAlbums(forceRefresh = true).test().assertValue {
            it.first().id != album.id
            it.first().title == album.title
        }
    }

    @Test
    fun getAlbumFromCacheTest() {
        every { api.albumDAO.getAlbums() } returns Single.error(Exception())
        every { localDb.localDB.albums().getAll() } returns Single.just(listOf(cacheAlbum))

        subject.getAlbums(forceRefresh = false).test().assertValue {
            it.first().id != album.id
            it.first().title == album.title
        }
    }

    @Test
    fun getPhotoFromNetworkTest() {
        every { api.albumDAO.getAlbumPhotos(album.id) } returns Single.just(listOf(apiPhoto))
        every { localDb.localDB.photos().getByAlbumId(album.id) } returns Single.just(emptyList())

        subject.getPhotos(album.id).test().assertValue {
            it.first().id != photo.id
            it.first().title == photo.title
        }
    }

    @Test
    fun getPhotoFromCacheTest() {
        every { api.albumDAO.getAlbumPhotos(album.id) } returns Single.error(Exception())
        every { localDb.localDB.photos().getByAlbumId(album.id) } returns Single.just(listOf(cachePhoto))

        subject.getPhotos(album.id).test().assertValue {
            it.first().id != photo.id
            it.first().title == photo.title
        }
    }
}