package br.com.sigga.service.listeners

import br.com.sigga.data.model.Album

interface AlbumView {
    fun onSuccess(data: List<Album>)
    fun onError(error: String)
}