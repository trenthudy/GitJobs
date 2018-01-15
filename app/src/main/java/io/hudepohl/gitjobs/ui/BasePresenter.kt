package io.hudepohl.gitjobs.ui

import android.support.annotation.CallSuper

/**
 * Created by trent on 1/13/18.
 */

abstract class BasePresenter<T> {

    protected var view: T? = null

    @CallSuper
    open fun bind(bindingView: T) {
        view = bindingView
    }

    @CallSuper
    open fun detach() {
        view = null
    }
}
