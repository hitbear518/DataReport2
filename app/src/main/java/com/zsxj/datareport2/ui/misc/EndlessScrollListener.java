package com.zsxj.datareport2.ui.misc;

import android.widget.AbsListView;
import android.widget.ListView;

import com.zsxj.datareport2.model.HttpResult;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-20.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

	private boolean mLoading = false;

	private ListView mLeftSideView;

	public EndlessScrollListener(ListView leftSideView) {
		mLeftSideView = leftSideView;
		EventBus.getDefault().register(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (totalItemCount > 0) {
			mLeftSideView.post(() -> {
				mLeftSideView.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
			});
			if (!mLoading && (totalItemCount - visibleItemCount - firstVisibleItem) <= 1) {
				onLoadMore();
				mLoading = true;
			}
		}
	}

	public abstract void onLoadMore();

	public void onEvent(HttpResult result) {
		mLoading = false;
	}
}
