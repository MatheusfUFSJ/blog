package br.com.sigga.service.listeners

import br.com.sigga.data.model.Post

interface PostView {
    fun onSuccess(data: List<Post>)
    fun onError(error: String)
}