package br.com.sigga.blog.viewmodels.fragments

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sigga.blog.adapters.AlbumAdapter
import br.com.sigga.blog.viewmodels.items.AlbumItemViewModel
import br.com.sigga.blog.viewmodels.states.AlbumState
import br.com.sigga.data.model.Album
import br.com.sigga.service.AlbumService
import br.com.sigga.service.listeners.AlbumView
import org.koin.core.KoinComponent
import org.koin.core.inject

class AlbumViewModel(
    private val adapter: AlbumAdapter
) : ViewModel(), KoinComponent, AlbumView {

    val state = ObservableField<AlbumState>()
    private val albumService: AlbumService by inject()

    fun findAll() {
        state.set(AlbumState.PROCESS)
        albumService.searchAlbum(this)
    }

    override fun onSuccess(data: List<Album>) {
        state.set(AlbumState.SUCCESS)
        update(data, true)
    }

    override fun onError(error: String) {
        state.set(AlbumState.ERROR)
    }

    private fun update(items: List<Album>, clear: Boolean) {
        adapter.update(items.map { ev -> AlbumItemViewModel.fromAlbum(ev) }, clear)
    }

    //region ~~~~ Factory ~~~~
    class Factory(
        private val adapter: AlbumAdapter
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            AlbumViewModel(adapter) as T
    }

}