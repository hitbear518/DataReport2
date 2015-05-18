package com.zsxj.datareport2.ui.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.ReturnEndDateEvent;
import com.zsxj.datareport2.event.ReturnStartDateEvent;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.ui.adapter.DateAdapter;
import com.zsxj.datareport2.ui.adapter.DaySalesAdapter;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

/**
 * Created by sen on 15-5-17.
 * Last Modified by
 */
@EFragment(R.layout.fragment_day_sales2)
public class DaySalesFragment2 extends BaseFragment {

	@ViewById(R.id.start_date_button)
	Button mStartDateButton;
	@ViewById(R.id.end_date_button)
	Button mEndDateButton;
	@ViewById(R.id.date_list)
	ListView mDateList;
	@ViewById(R.id.day_sales_list)
	ListView mDaySalesList;
	@ViewById(R.id.fab)
	FloatingActionButton mFab;

	View clickSource;
	View touchSource;

	@Bean
	RequestHelper mRequestHelper;
	DaySalesResult mResult;

	@AfterViews
	void init() {
		mStartDateButton.setText("2014-07-01");
		mEndDateButton.setText("2014-09-01");
		requestData();

		mDateList.setOnTouchListener(((v, event) -> {
			if (touchSource == null) {
				touchSource = v;
			}

			if (v == touchSource) {
				mDaySalesList.dispatchTouchEvent(event);
				if (event.getAction() == MotionEvent.ACTION_UP) {
					clickSource = v;
					touchSource = null;
				}
			}
			return false;
		}));
		mDateList.setOnItemClickListener((parent, view, position, id) -> {
			if (parent == clickSource) {

			}
		});
		mDateList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (view == clickSource) {
					mDaySalesList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
				}
			}
		});
		mDaySalesList.setOnTouchListener((v, event) -> {
			if (touchSource == null) {
				touchSource = v;
			}

			if (v == touchSource) {
				mDateList.dispatchTouchEvent(event);
				if (event.getAction() == MotionEvent.ACTION_UP) {
					clickSource = v;
					touchSource = null;
				}
			}
			return false;
		});
		mDaySalesList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (clickSource == view) {
					mDateList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
				}
			}
		});
		mDaySalesList.setOnItemClickListener((parent, view, position, id) -> {
			if (parent == clickSource) {

			}
		});
	}

	private View.OnTouchListener mOnTouchListener = (v, event) -> {
		if (touchSource == null) {
			touchSource = v;
		}

		if (v == touchSource) {
			mDaySalesList.dispatchTouchEvent(event);
			if (event.getAction() == MotionEvent.ACTION_UP) {
				clickSource = v;
				touchSource = null;
			}
		}
		return false;
	};

	private AbsListView.OnScrollListener mDateScrollListener = new AbsListView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				int index = view.getFirstVisiblePosition();
				View v = view.getChildAt(0);
				int top = (v == null) ? 0 : (v.getTop() - view.getPaddingTop());
				mDaySalesList.smoothScrollToPositionFromTop(index, top, 0);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};

	@Click(R.id.start_date_button)
	void startDateClicked() {
		LocalDate initialDate = Utils.FORMATTER.parseLocalDate(mStartDateButton.getText().toString());
		DatePickerFragment_.builder()
			.mInitialDate(initialDate)
			.mEvent(new ReturnStartDateEvent())
			.build()
			.show(getFragmentManager(), null);
	}

	@Click(R.id.end_date_button)
	void endDateClicked() {
		LocalDate initialDate = Utils.FORMATTER.parseLocalDate(mEndDateButton.getText().toString());
		DatePickerFragment_.builder()
			.mInitialDate(initialDate)
			.mEvent(new ReturnEndDateEvent())
			.build()
			.show(getFragmentManager(), null);
	}

	public void onEvent(DaySalesResult result) {
		mResult = result;
		fillList(mResult);
	}

	private void fillList(DaySalesResult result) {
		showProgress(false);
		mDateList.setAdapter(new DateAdapter(result.stat_sales_sell_list));
		mDaySalesList.setAdapter(new DaySalesAdapter(result.stat_sales_sell_list));
	}

	private void requestData() {
		showProgress(true);
		mRequestHelper.queryDaySales(mStartDateButton.getText().toString(), mEndDateButton.getText().toString());
	}

	public void onEventMainThread(ReturnStartDateEvent event) {
		mStartDateButton.setText(event.date);
	}

	public void onEventMainThread(ReturnEndDateEvent event) {
		mEndDateButton.setText(event.date);
	}
}
