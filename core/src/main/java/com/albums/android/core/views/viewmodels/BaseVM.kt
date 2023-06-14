package com.albums.android.core.views.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseVM: ViewModel() {

    val disposeBag = CompositeDisposable()

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }
}