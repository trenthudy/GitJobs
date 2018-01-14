package io.hudepohl.gitjobs.ui

/**
 * Created by trent on 1/13/18.
 */

abstract class BasePresenter<T> {

    protected var view: T? = null

    fun bind(bindingView: T) {
        view = bindingView
    }

    fun detach() {
        view = null
    }
}
