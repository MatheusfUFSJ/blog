package br.com.sigga.service.listeners

import br.com.sigga.data.model.Comment

interface CommentView {
    fun onSuccess(data: List<Comment>)
    fun onError(error: String)
}