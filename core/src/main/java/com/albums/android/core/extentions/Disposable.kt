package com.albums.android.core.extentions

import com.albums.android.core.views.viewmodels.BaseVM
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.disposeWith(baseViewModel: BaseVM) = addTo(baseViewModel.disposeBag)

fun Disposable.addTo(disposeBag: CompositeDisposable) = disposeBag.add(this)