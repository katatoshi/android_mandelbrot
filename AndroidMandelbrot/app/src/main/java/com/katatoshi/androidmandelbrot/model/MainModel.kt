package com.katatoshi.androidmandelbrot.model

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.katatoshi.androidmandelbrot.BR

/**
 * メインの model。
 */
object MainModel : BaseObservable() {

    @set:Bindable
    var sampleText = "Hello World!"
        set(sampleText) {
            if (this.sampleText == sampleText) {
                return
            }

            field = sampleText
            notifyPropertyChanged(BR.sampleText)
        }

    @set:Bindable
    var counter = 0
        set(counter) {
            if (this.counter == counter) {
                return
            }

            field = counter
            notifyPropertyChanged(BR.counter)

            if (0 < counter) {
                sampleText = "Hello World! ($counter)"
            }
        }
}
