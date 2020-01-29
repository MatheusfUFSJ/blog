package br.com.sigga.api

import android.annotation.SuppressLint
import br.com.in8.coins.servives.restapi.NetworkModule
import br.com.in8.coins.utils.SchedulerUtils
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.api.rest.AlbumRest
import br.com.sigga.data.model.Album
import br.com.sigga.data.model.Photo
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

@SuppressLint("CheckResult")
class AlbumApiService : AnkoLogger {

    private val service: AlbumRest by lazy { NetworkModule.createService(AlbumRest::class.java) }

    fun searchAlbum(callback: ApiView<Album>) {
        service.getAlbums()
                .observeOn(SchedulerUtils.observeOn())
                .subscribeOn(SchedulerUtils.subscribeOn())
                .subscribe({
                    callback.onSuccess(it)
                }, { e ->
                    error(e.message, e)
                    callback.onError(e)
                })
    }

    fun searchPhotos(callback: ApiView<Photo>) {
        service.getPhotos()
            .observeOn(SchedulerUtils.observeOn())
            .subscribeOn(SchedulerUtils.subscribeOn())
            .subscribe({
                callback.onSuccess(it)
            }, { e ->
                error(e.message, e)
                callback.onError(e)
            })
    }

    fun searchPhotos(id: String, callback: ApiView<Photo>) {
        service.getPhotos(id)
            .observeOn(SchedulerUtils.observeOn())
            .subscribeOn(SchedulerUtils.subscribeOn())
            .subscribe({
                callback.onSuccess(it)
            }, { e ->
                error(e.message, e)
                callback.onError(e)
            })
    }

}