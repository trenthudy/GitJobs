package io.hudepohl.gitjobs.ui

/**
 * Created by trent on 1/13/18.
 */
open class BasePresenter {

    protected var view: Any? = null

    fun bindView(newView: Any) {
        view = newView
    }

    fun detachView() {
        view = null
    }
}
