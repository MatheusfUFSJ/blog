package br.com.sigga.api.listeners

interface ApiView<T> {
    fun onSuccess(data: List<T>)
    fun onError(error: Throwable)
}