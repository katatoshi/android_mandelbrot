package com.katatoshi.androidmandelbrot.extension

import android.databinding.BaseObservable
import android.databinding.Observable

/**
 * android.databinding に対する拡張
 */

private var callbackMap: MutableMap<(Observable, Int) -> Unit, Observable.OnPropertyChangedCallback> = mutableMapOf()

fun BaseObservable.addOnPropertyChangedCallback(callback: (Observable, Int) -> Unit) {
    if (callbackMap.containsKey(callback)) {
        return
    }

    val onPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            callback(sender, propertyId)
        }
    }
    callbackMap[callback] = onPropertyChangedCallback
    this.addOnPropertyChangedCallback(onPropertyChangedCallback)
}

fun BaseObservable.removeOnPropertyChangedCallback(callback: (Observable, Int) -> Unit) {
    if (!callbackMap.containsKey(callback)) {
        return
    }

    this.removeOnPropertyChangedCallback(callbackMap[callback])
    callbackMap.remove(callback)
}
