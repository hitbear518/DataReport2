package com.zsxj.datareport2.ui.misc;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sen on 15-5-19.
 */
public class ListScrollSyncer implements AbsListView.OnScrollListener, View.OnTouchListener, GestureDetector.OnGestureListener {

	private GestureDetector mGestureDetector;
	private Set<ListView> mListSet = new HashSet<>();
	private ListView mCurrentTouchSource;

	private int mCurrentOffset = 0;
	private int mCurrentPosition = 0;

	private boolean mScrolling;

	public void addList(ListView list) {
		mListSet.add(list);
		list.setOnTouchListener(this);
		list.setSelectionFromTop(mCurrentPosition, mCurrentOffset);

		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(list.getContext(), this);
		}
	}

	public void removeList(ListView list) {
		mListSet.remove(list);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return !mScrolling;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mScrolling = scrollState != SCROLL_STATE_IDLE;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (view.getChildCount() > 0) {
			mCurrentPosition = view.getFirstVisiblePosition();
			mCurrentOffset = view.getChildAt(0).getTop();
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ListView list = (ListView) v;
		if (mCurrentTouchSource != null) {
			list.setOnScrollListener(null);
			return mGestureDetector.onTouchEvent(event);
		} else {
			list.setOnScrollListener(this);
			mCurrentTouchSource = list;

			for (ListView l : mListSet) {
				if (l != mCurrentTouchSource) {
					l.dispatchTouchEvent(event);
				}
			}
			mCurrentTouchSource = null;
			return false;
		}
	}
}
