package br.com.sigga.api.rest

import br.com.sigga.data.model.User
import io.reactivex.Observable
import retrofit2.http.GET

interface UserRest {

    @GET("users/")
    fun getUsers(): Observable<List<User>>
}