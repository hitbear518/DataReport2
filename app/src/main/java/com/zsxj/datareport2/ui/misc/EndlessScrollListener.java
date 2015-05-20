package com.zsxj.datareport2.ui.misc;

import android.widget.AbsListView;

/**
 * Created by sen on 15-5-20.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

	private int mVisibleThreshold = 1;

	private int mCurrentPage = 0;

	private boolean mLoading = true;

	public EndlessScrollListener(int visibleThreshold) {
		mVisibleThreshold = visibleThreshold;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		if (!mLoading && (totalItemCount - visibleItemCount) <= mVisibleThreshold) {

		}
	}

	public abstract void onLoadMore(int page, int totalItemCount);
}
