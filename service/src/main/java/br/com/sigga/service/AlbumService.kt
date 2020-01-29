package br.com.sigga.service

import android.annotation.SuppressLint
import android.content.Context
import br.com.sigga.api.AlbumApiService
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.data.dao.AlbumDao
import br.com.sigga.data.dao.PhotoDao
import br.com.sigga.data.model.Album
import br.com.sigga.data.model.Photo
import br.com.sigga.service.listeners.AlbumView
import br.com.sigga.service.listeners.PhotoView
import br.com.sigga.service.utils.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("CheckResult")
class AlbumService : BaseService(), KoinComponent, AnkoLogger {

    private val albumApi: AlbumApiService by lazy { AlbumApiService() }
    private val photoDao: PhotoDao by inject()
    private val albumDao: AlbumDao by inject()

    fun searchAlbum(listener: AlbumView) {
        searchAlbumOnline(object : AlbumView {
            override fun onSuccess(data: List<Album>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                findAlbumDb(listener)
            }
        })
    }

    fun searchPhoto(context: Context, listener: PhotoView) {
        searchPhotoOnline(context, object : PhotoView {
            override fun onSuccess(data: List<Photo>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                findPhotoDb(listener)
            }
        })
    }

    fun findPhotoDb(listener: PhotoView) {
        Observable.fromCallable {
            photoDao.getPhoto()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSuccess(it)
            }, {
                error(it.message, it)
                listener.onError(ERROR_DB)
            })
    }

    fun findAlbumDb(listener: AlbumView) {
        Observable.fromCallable {
            albumDao.getAlbum()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSuccess(it)
            }, {
                error(it.message, it)
                listener.onError(ERROR_DB)
            })
    }


    private fun savePhoto(
        context: Context, data: List<Photo>, success: () -> Unit, error: () -> Unit) {

        Observable.fromCallable {
            loadImagesToCache(
                context,
                data.map(Photo::thumbnailUrl).filterNot(String::isNullOrEmpty)
            )
            photoDao.insertAll(data)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
            }, {
                error(it.message, it)
                error()
            })
    }

    private fun saveAlbum(
        data: List<Album>, success: () -> Unit,
        error: () -> Unit
    ) {
        Observable.fromCallable {
            albumDao.insertAll(data)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
            }, {
                error(it.message, it)
                error()
            })
    }

    private fun searchAlbumOnline(listener: AlbumView) {
        albumApi.searchAlbum(object : ApiView<Album> {
            override fun onSuccess(data: List<Album>) {
                saveAlbum(data, {
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

    private fun searchPhotoOnline(context: Context, listener: PhotoView) {
        albumApi.searchPhotos(object : ApiView<Photo> {
            override fun onSuccess(data: List<Photo>) {
                savePhoto(context, data, {
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

    private fun loadImagesToCache(context: Context, urls: List<String>) {
        urls.forEach { url ->
            if (url.isNotEmpty()) {
                try {
                    GlideApp.with(context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .submit()
                        .get()
                } catch (ex: Exception) {
                    error(ex.message, ex)
                }
            }
        }
    }

}