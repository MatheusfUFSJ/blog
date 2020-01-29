package br.com.sigga.service

import android.annotation.SuppressLint
import br.com.sigga.api.PostApiService
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.data.dao.PostDao
import br.com.sigga.data.model.Post
import br.com.sigga.service.listeners.PostView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("CheckResult")
class PostService : BaseService(), KoinComponent, AnkoLogger {

    private val postApi: PostApiService by lazy { PostApiService() }
    private val postDao: PostDao by inject()

    fun searchPost(listener: PostView) {
        searchPostOnline(object : PostView {
            override fun onSuccess(data: List<Post>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                findPostDb(listener)
            }

        })
    }

    fun findPostDb(listener: PostView) {
        Observable.fromCallable {
            postDao.getPost()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSuccess(it)
            }, {
                error(it.message, it)
                listener.onError(ERROR_DB)
            })
    }

    private fun savePost(data: List<Post>, success: () -> Unit,
                         error: () -> Unit) {
        Observable.fromCallable {
                postDao.insertAll(data)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
            }, {
                error(it.message, it)
                error()
            })
    }

    private fun searchPostOnline(listener: PostView) {
        postApi.searchPost(object : ApiView<Post> {
            override fun onSuccess(data: List<Post>) {
                savePost(data, {
                    listener.onSuccess(data)
                }, {
                    listener.onSuccess(data)
                })
            }
            override fun onError(error: Throwable) {
                listener.onError(ERROR_INTERNET)
            }

        })
    }
}