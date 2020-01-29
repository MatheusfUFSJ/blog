package br.com.sigga.service

import android.annotation.SuppressLint
import br.com.sigga.api.PostApiService
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.data.dao.CommentDao
import br.com.sigga.data.model.Comment
import br.com.sigga.service.listeners.CommentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("CheckResult")
class CommentService : BaseService(), KoinComponent, AnkoLogger {


    private val postApi: PostApiService by lazy { PostApiService() }
    private val commentDao: CommentDao by inject()

    fun searchComment(id: Long, listener: CommentView) {
        searchCommentOnline(id, object : CommentView {
            override fun onSuccess(data: List<Comment>) {
                listener.onSuccess(data.filter { d -> d.postId == id })
            }

            override fun onError(error: String) {
                findCommentDb(id, listener)
            }
        })
    }

    fun findCommentDb(id: Long, listener: CommentView) {
        Observable.fromCallable {
            commentDao.getComment(id)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSuccess(it)
            }, {
                error(it.message, it)
                listener.onError(ERROR_DB)
            })
    }

    private fun saveComment(
        data: List<Comment>, success: () -> Unit,
        error: () -> Unit
    ) {
        Observable.fromCallable {
            commentDao.insertAll(data)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
            }, {
                error(it.message, it)
                error()
            })
    }

    private fun searchCommentOnline(id: Long, listener: CommentView) {
        postApi.searchComments(id, object : ApiView<Comment> {
            override fun onSuccess(data: List<Comment>) {
                saveComment(data, {
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