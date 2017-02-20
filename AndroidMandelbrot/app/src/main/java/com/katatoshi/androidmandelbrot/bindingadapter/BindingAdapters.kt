package com.katatoshi.androidmandelbrot.bindingadapter

import android.databinding.BindingAdapter
import android.view.View

/**
 * カスタムセッター。
 * Java に変換したときに static メソッドになる必要があるので、トップレベルの関数として定義する。
 * カスタムセッターは XML から定義に飛べず、しかも重複して定義できるので（重複して定義したときにどれが優先されるかはわからない）一箇所にまとめておく。
 * カスタム View に対するカスタムセッターもこちらに置くべきかは考え中。
 */

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean) {
    this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("gone")
fun View.setGone(gone: Boolean) {
    this.visibility = if (gone) View.GONE else View.VISIBLE
}
