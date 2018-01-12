package io.hudepohl.gitjobs.util;

import android.widget.AbsListView;

/**
 * Created by trent on 1/11/18.
 */

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 1;
    private int divisibilityTest = 50;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount ) {
            if (totalItemCount % divisibilityTest == 0) {
                loading = onLoadMore(currentPage + 1, totalItemCount);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

}
