package br.com.sigga.api

import android.annotation.SuppressLint
import br.com.in8.coins.servives.restapi.NetworkModule
import br.com.in8.coins.utils.SchedulerUtils
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.api.rest.PostRest
import br.com.sigga.data.model.Comment
import br.com.sigga.data.model.Post
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

@SuppressLint("CheckResult")
class PostApiService : AnkoLogger {

    private val service: PostRest by lazy { NetworkModule.createService(PostRest::class.java) }

    fun searchPost(callback: ApiView<Post>) {
        service.getPosts()
            .observeOn(SchedulerUtils.observeOn())
            .subscribeOn(SchedulerUtils.subscribeOn())
            .subscribe({
                callback.onSuccess(it)
            }, { e ->
                error(e.message, e)
                callback.onError(e)
            })
    }

    fun searchComments(id: Long, callback: ApiView<Comment>) {
        service.getComments(id)
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