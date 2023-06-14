package com.albums.android.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.albums.android.core.extentions.addTo
import com.albums.android.core.models.Album
import com.albums.android.core.repositories.AlbumRepository
import com.albums.android.databinding.ListItemAlbumBinding
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlbumsAdapter : ListAdapter<Album, AlbumViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemAlbumBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem.title == newItem.title
        }
    }
}

class AlbumViewHolder(private val binding: ListItemAlbumBinding) : ViewHolder(binding.root), KoinComponent {

    private val albumRepository: AlbumRepository by inject()
    private val disposeBag = CompositeDisposable()

    fun bind(album: Album) = with(binding) {
        disposeBag.clear()

        tvTitle.text = album.title

        albumRepository.getPhotos(album.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Glide.with(root).load(it.getOrNull(0)?.url).into(iv1)
                Glide.with(root).load(it.getOrNull(1)?.url).into(iv2)
                Glide.with(root).load(it.getOrNull(2)?.url).into(iv3)
            }, {
                Logger.e("Error fetching photos: $it")
            }).addTo(disposeBag)
    }
}