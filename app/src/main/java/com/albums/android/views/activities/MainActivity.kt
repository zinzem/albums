package com.albums.android.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.albums.android.R
import com.albums.android.core.views.activities.BaseActivity
import com.albums.android.databinding.ActivityMainBinding
import com.albums.android.views.adapters.AlbumsAdapter
import com.albums.android.views.viewmodels.MainActivityVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityVM by viewModel()
    private val adapter = AlbumsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()

        viewModel.albums.observe(this) {
            adapter.submitList(it)
        }
        viewModel.error.observe(this) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
        viewModel.loading.observe(this) { loading ->
            binding.fabRefresh.isVisible = !loading
            binding.fabLoading.isVisible = loading
            binding.pLoading.isVisible = loading
        }
        viewModel.getAlbumsAlphabetically(false)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_refresh -> viewModel.getAlbumsAlphabetically(true)
        }
    }

    private fun setupRecyclerView() = binding.rvAlbums.apply {
        adapter = this@MainActivity.adapter
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
    }

    private fun setupFab() = binding.fabRefresh.setOnClickListener(this)
}