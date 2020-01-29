package br.com.sigga.api.rest

import br.com.sigga.data.model.Album
import br.com.sigga.data.model.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumRest {


    @GET("photos/")
    fun getPhotos(): Observable<List<Photo>>

    @GET("albums/")
    fun getAlbums(): Observable<List<Album>>

    @GET("albums/{id}/photos")
    fun getPhotos(@Path("id") id: String): Observable<List<Photo>>
}