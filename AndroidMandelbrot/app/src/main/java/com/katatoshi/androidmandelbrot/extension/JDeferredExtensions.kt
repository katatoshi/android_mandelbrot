package com.katatoshi.androidmandelbrot.extension

import org.jdeferred.DeferredManager
import org.jdeferred.Promise

/**
 * org.jdeferred に対する拡張
 */

fun <D : Any> DeferredManager.of(f: () -> D): Promise<D, Throwable, Void> {
    return this.`when`(f)
}

fun <D : Any, F : Any, P : Any, E : Any> Promise<D, F, P>.map(f: (D) -> E): Promise<E, F, P> {
    return this.then(f)
}

fun <D : Any, F : Any, P : Any, E : Any> Promise<D, F, P>.flatMap(f: (D) -> Promise<E, F, P>): Promise<E, F, P> {
    return this.then(f)
}

fun DeferredManager.delay(millis: Long): Promise<Unit, Throwable, Void> {
    return this.of { Thread.sleep(millis) }
}

fun <D : Any, F : Any, P : Any> Promise<D, F, P>.delay(millis: Long): Promise<D, F, P> {
    return this.flatMap {
        Thread.sleep(millis)
        this
    }
}
