package io.hudepohl.gitjobs.util

import android.widget.AbsListView

abstract class EndlessScrollListener(
        private val unitsPerPage: Int,
        private val visibilityThreshold: Int) : AbsListView.OnScrollListener {

    private var page: Int = 0
    private var itemCount = unitsPerPage
    private var loading = false

    override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                          visibleItemCount: Int, totalItemCount: Int) {

        if (loading && totalItemCount > itemCount) {
            loading = false
            itemCount = totalItemCount
        }

        if (!loading
                && totalItemCount % unitsPerPage == 0
                && firstVisibleItem + visibleItemCount + visibilityThreshold >= totalItemCount) {

            loading = onLoadMore(++page, totalItemCount)
        }
    }

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean
}
