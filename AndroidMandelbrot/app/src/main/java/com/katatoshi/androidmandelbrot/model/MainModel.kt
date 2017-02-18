package com.katatoshi.androidmandelbrot.model

import android.databinding.BaseObservable
import android.databinding.Bindable

import com.google.common.base.Objects
import com.katatoshi.androidmandelbrot.BR

/**
 * メインの model。
 */
object MainModel : BaseObservable() {

    @set:Bindable
    var sampleText = "Hello World!"
        set(sampleText) {
            if (Objects.equal(this.sampleText, sampleText)) {
                return
            }

            field = sampleText
            notifyPropertyChanged(BR.sampleText)
        }
}
