package com.zsxj.datareport2.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.ReturnEndDateEvent;
import com.zsxj.datareport2.event.ReturnStartDateEvent;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.Warehouse;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.ui.adapter.DateAdapter;
import com.zsxj.datareport2.ui.adapter.DaySalesAdapter;
import com.zsxj.datareport2.ui.widget.ScrollDisabledListView;
import com.zsxj.datareport2.utils.DefaultPrefs_;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.joda.time.LocalDate;

import java.util.List;

import java8.util.stream.StreamSupport;

/**
 * Created by sen on 15-5-17.
 * Last Modified by
 */
@EFragment(R.layout.fragment_day_sales2)
@OptionsMenu(R.menu.menu_day_sales)
public class DaySalesFragment2 extends BaseFragment {

	@ViewById(R.id.start_date_button)
	Button mStartDateButton;
	@ViewById(R.id.end_date_button)
	Button mEndDateButton;
	@ViewById(R.id.date_list)
	ScrollDisabledListView mDateList;
	@ViewById(R.id.day_sales_list)
	ListView mDaySalesList;
	@ViewById(R.id.fab)
	FloatingActionButton mFab;

	@Bean
	RequestHelper mRequestHelper;

	@OptionsMenuItem(R.id.action_select_warehouses)
	MenuItem mSelectWarehouseMenuItem;

	@Pref
	DefaultPrefs_ mDefaultPrefs;

	private DaySalesResult mResult;

	@AfterViews
	void init() {
		mStartDateButton.setText("2014-07-01");
		mEndDateButton.setText("2014-09-01");
		requestData();

		mDaySalesList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@SuppressLint("NewApi")
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (view.getChildCount() > 0) {
					mDateList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
				}

			}
		});
		mFab.attachToListView(mDaySalesList);
	}

	@OptionsItem(R.id.action_select_warehouses)
	void selectionWarehouses() {
		String warehousesStr = mDefaultPrefs.warehouses().get();
		List<Warehouse> warehouses = Utils.toList(warehousesStr, Warehouse.class);
		Boolean[] temp = StreamSupport.stream(warehouses).map(w -> w.checked).toArray(size -> new Boolean[size]);
		boolean[] checkedItems = new boolean[temp.length];
		for (int i = 0; i < temp.length; i++) {
			checkedItems[i] = temp[i];
		}
		CharSequence[] names = StreamSupport.stream(warehouses).map(w -> w.name).toArray(size -> new CharSequence[size]);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMultiChoiceItems(names, checkedItems, (dialog, which, isChecked) -> {
			warehouses.get(which).checked = isChecked;
		}).setPositiveButton(android.R.string.ok, (dialog, which) -> {
			long count = StreamSupport.stream(warehouses).filter(w -> w.checked).count();
			String title = count > 0 ? getString(R.string.selected_warehouse_count_format, count) : getString(R.string.warehouse);
			mSelectWarehouseMenuItem.setTitle(title);
			mDefaultPrefs.warehouses().put(Utils.toJson(warehouses));
		}).show();
	}

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

	@Click(R.id.fab)
	void requestData() {
		showProgress(true);
		mRequestHelper.queryDaySales(mStartDateButton.getText().toString(), mEndDateButton.getText().toString());
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

	public void onEventMainThread(ReturnStartDateEvent event) {
		mStartDateButton.setText(event.date);
	}

	public void onEventMainThread(ReturnEndDateEvent event) {
		mEndDateButton.setText(event.date);
	}
}
