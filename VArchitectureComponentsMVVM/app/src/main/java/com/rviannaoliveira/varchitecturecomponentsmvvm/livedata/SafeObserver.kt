package com.rviannaoliveira.varchitecturecomponentsmvvm.livedata
import androidx.lifecycle.Observer

/**
 * An alternative for the default Observer used for listening changes on LiveData fields.
 * It already handles the case in which the field is null.
 *
 * Example:
 * liveField.observe(this, SafeObserver {
 *      doSomething(it)
 * })
 *
 */
class SafeObserver<T>(private val callback: (T) -> Unit): Observer<T> {
    override fun onChanged(t: T?) {
        t?.let {
            callback(it)
        }
    }
}
