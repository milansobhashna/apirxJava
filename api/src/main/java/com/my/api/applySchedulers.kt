package com.my.api

import android.app.Activity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.ConnectException

class applySchedulers(val mainActivity: Activity) {

    private val mSchedulersTransformer =
        Observable.Transformer { observable: Observable<Any> ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    fun <T> applySchedulers(): Observable.Transformer<T, T> {
        return mSchedulersTransformer as Observable.Transformer<T, T>
    }

    fun <T> call(apiObservable: Observable<T>): Observable<T> {
        return apiObservable.startWith(Observable.defer {
            //before calling each api, network connection is checked.
            val netchk = networkConnection(mainActivity)
            if (!netchk.isConnected()) {
                //if network is not available, it will return error observable with ConnectException.
                return@defer Observable.error<T>(ConnectException("Device is not connected to network"))
            } else {
                //if it is available, it will return empty observable. Empty observable just emits onCompleted() immediately
                return@defer Observable.empty<T>()
            }
        })
            .doOnNext { response: T ->
                //logging response on success
                //you can change to to something else
                //for example, if all your apis returns error codes in success, then you can throw custom exception here

            }
            .doOnError { throwable: Throwable ->
                //printing stack trace on error

            }
    }
}