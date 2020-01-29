package br.com.sigga.service.listeners

import br.com.sigga.data.model.Photo

interface PhotoView {
    fun onSuccess(data: List<Photo>)
    fun onError(error: String)
}