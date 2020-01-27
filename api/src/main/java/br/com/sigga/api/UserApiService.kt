package br.com.sigga.api

import android.annotation.SuppressLint
import br.com.in8.coins.servives.restapi.NetworkModule
import br.com.in8.coins.utils.SchedulerUtils
import br.com.sigga.api.listeners.ApiView
import br.com.sigga.api.rest.UserRest
import br.com.sigga.data.model.User
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

@SuppressLint("CheckResult")
class UserApiService : AnkoLogger {

    private val service: UserRest by lazy { NetworkModule.createService(UserRest::class.java) }

    fun searchUser(callback: ApiView<User>) {
        service.getUsers()
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