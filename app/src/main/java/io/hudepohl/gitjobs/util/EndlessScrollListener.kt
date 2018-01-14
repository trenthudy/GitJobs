package io.hudepohl.gitjobs.util

import android.widget.AbsListView

/**
 * Created by trent on 1/11/18.
 */

abstract class EndlessScrollListener : AbsListView.OnScrollListener {

    private val visibleThreshold = 5
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private val divisibilityTest = 50

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        if (!loading && firstVisibleItem + visibleItemCount + visibleThreshold >= totalItemCount) {
            if (totalItemCount % divisibilityTest == 0) {
                loading = onLoadMore(currentPage + 1, totalItemCount)
            }
        }
    }

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean
}
