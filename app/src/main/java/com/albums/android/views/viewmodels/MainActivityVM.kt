package com.albums.android.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.albums.android.core.extentions.disposeWith
import com.albums.android.core.models.Album
import com.albums.android.core.repositories.AlbumRepository
import com.albums.android.core.views.viewmodels.BaseVM
import com.orhanobut.logger.Logger

class MainActivityVM(
    private val albumRepository: AlbumRepository
) : BaseVM() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAlbumsAlphabetically(
        forceRefresh: Boolean = false
    ) = albumRepository.getAlbums(forceRefresh)
        .doOnSubscribe { _loading.postValue(true) }
        .flattenAsObservable { it }
        .sorted { a, b -> when {
            a.title > b.title -> 1
            a.title < b.title -> -1
            else -> 0
        } }.toList().subscribe({
            _loading.postValue(false)
            _albums.postValue(it)
        }, {
            Logger.e("Error fetching albums: $it")
            _loading.postValue(false)
            _error.postValue(it.toString())
        }).disposeWith(this)
}