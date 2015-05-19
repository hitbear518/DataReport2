package com.zsxj.datareport2.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by sen on 15-5-19.
 */
public class ScrollDisabledListView extends ListView {

	private int mPosition;

	public ScrollDisabledListView(Context context) {
		super(context);
	}

	public ScrollDisabledListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollDisabledListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final int actionMasked = ev.getActionMasked() & MotionEvent.ACTION_MASK;

		if (actionMasked == MotionEvent.ACTION_DOWN) {
//			mPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
//			return super.dispatchTouchEvent(ev);
			return true;
		}

		if (actionMasked == MotionEvent.ACTION_MOVE) {
			return true;
		}

		if (actionMasked == MotionEvent.ACTION_UP) {
//			if (pointToPosition((int) ev.getX(), (int) ev.getY()) == mPosition) {
//				super.dispatchTouchEvent(ev);
//			} else {
//				setPressed(false);
//				invalidate();
//				return true;
//			}
			return true;
		}

		return super.dispatchTouchEvent(ev);
	}
}
