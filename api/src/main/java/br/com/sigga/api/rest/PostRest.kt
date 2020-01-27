package br.com.sigga.api.rest

import br.com.sigga.data.model.Comment
import br.com.sigga.data.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostRest {
    @GET("posts/")
    fun getPosts(): Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: String): Observable<List<Comment>>
}