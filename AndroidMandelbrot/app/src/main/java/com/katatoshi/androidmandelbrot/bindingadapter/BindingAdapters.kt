package com.katatoshi.androidmandelbrot.bindingadapter

import android.databinding.BindingAdapter
import android.view.View

/**
 * カスタムセッター
 * Java に変換したときに static メソッドになる必要があるので、トップレベルの関数として定義する
 */

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean) {
    this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("gone")
fun View.setGone(gone: Boolean) {
    this.visibility = if (gone) View.GONE else View.VISIBLE
}
