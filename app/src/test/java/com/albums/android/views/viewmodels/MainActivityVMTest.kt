package com.albums.android.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albums.android.core.extentions.getOrAwaitValue
import com.albums.android.core.models.Album
import com.albums.android.core.repositories.AlbumRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityVMTest {

    private lateinit var subject: MainActivityVM

    private val albumRepository: AlbumRepository = mockk(relaxed = true)

    private val albums = listOf(
        Album("id", "def", "userId"),
        Album("id", "abc", "userId"),
        Album("id", "ghi", "userId")
    )

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }

        subject = MainActivityVM(albumRepository)
    }

    @Test
    fun getAlbumsFromNetworkTest() {
        subject.getAlbumsAlphabetically(true)

        verify {
            albumRepository.getAlbums(true)
        }
    }

    @Test
    fun getAlbumsFromCacheTest() {
        subject.getAlbumsAlphabetically(false)

        verify {
            albumRepository.getAlbums(false)
        }
    }

    @Test
    fun getAlbumsSortedTest() {
        every { albumRepository.getAlbums(true) } returns Single.just(albums)

        subject.getAlbumsAlphabetically(true)

        assert(subject.albums.getOrAwaitValue()[0].title == "abc")
        assert(subject.albums.getOrAwaitValue()[1].title == "def")
        assert(subject.albums.getOrAwaitValue()[2].title == "ghi")
        assert(!subject.loading.getOrAwaitValue())
    }

    @Test
    fun getAlbumsErrorTest() {
        every { albumRepository.getAlbums(true) } returns Single.error(Exception("error"))

        subject.getAlbumsAlphabetically(true)

        assert(subject.error.getOrAwaitValue().isNotEmpty())
        assert(!subject.loading.getOrAwaitValue())
    }
}
