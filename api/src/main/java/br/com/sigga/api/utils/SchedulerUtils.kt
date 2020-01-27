package br.com.in8.coins.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerUtils {

    var onScheduler = AndroidSchedulers.mainThread()
    var subscribe = Schedulers.io()

    fun observeOn() = onScheduler

    fun subscribeOn() = subscribe

}
