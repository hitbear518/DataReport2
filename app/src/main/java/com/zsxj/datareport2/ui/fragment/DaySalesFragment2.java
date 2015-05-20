package com.zsxj.datareport2.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.common.primitives.Booleans;
import com.melnykov.fab.FloatingActionButton;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.ReturnEndDateEvent;
import com.zsxj.datareport2.event.ReturnStartDateEvent;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.Shop;
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

import java8.util.stream.Collectors;
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

	@OptionsMenuItem(R.id.action_select_shop)
	MenuItem mSelectShopItem;
	@OptionsMenuItem(R.id.action_select_warehouses)
	MenuItem mSelectWarehouseMenuItem;

	@Pref
	DefaultPrefs_ mDefaultPrefs;

	private DaySalesResult mResult;

	private List<Shop> mShops;
	private List<Warehouse> mWarehouses;

	@AfterViews
	void init() {
		String shopsStr = mDefaultPrefs.shops().get();
		mShops = Utils.toList(shopsStr, Shop.class);
		String warehouseStr = mDefaultPrefs.warehouses().get();
		mWarehouses = Utils.toList(warehouseStr, Warehouse.class);
		mStartDateButton.setText("14-07-01");
		mEndDateButton.setText("14-09-01");
		initRequest();

		mDaySalesList.setOnScrollListener(new AbsListView.OnScrollListener() {

			private int previouseTotal;
			private boolean loading;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@SuppressLint("NewApi")
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (totalItemCount > 0) {
					mDateList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());

					if (!loading && totalItemCount < mResult.total_count && totalItemCount - (firstVisibleItem + visibleItemCount) == 1) {
						int pageNo = totalItemCount / RequestHelper.CURRENT_PAGE_SIZE;
						showProgress(true);
						previouseTotal = totalItemCount;
						mRequestHelper.queryDaySales(mStartDateButton.getText().toString(), mEndDateButton.getText().toString(), pageNo);
					}
				}

			}
		});
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		updateSelectShopItemTitle();
		updateSelectWarehouseItemTitle();
	}

	private void updateSelectShopItemTitle() {
		long count = StreamSupport.stream(mShops).filter(shop -> shop.checked).count();
		String title = count > 0 ? getString(R.string.selected_shop_count_format, count) : getString(R.string.shop);
		mSelectShopItem.setTitle(title);
	}

	private void updateSelectWarehouseItemTitle() {
		long count = StreamSupport.stream(mWarehouses).filter(w -> w.checked).count();
		String title = count > 0 ? getString(R.string.selected_warehouse_count_format, count) : getString(R.string.warehouse);
		mSelectWarehouseMenuItem.setTitle(title);
	}

	@OptionsItem(R.id.action_select_shop)
	void selectShop() {
		List<Boolean> listCheckedItem = StreamSupport.stream(mShops).map(shop -> shop.checked).collect(Collectors.toList());
		boolean[] checkedItems = Booleans.toArray(listCheckedItem);
		CharSequence[] items = StreamSupport.stream(mShops).map(shop -> shop.shop_name).toArray(size -> new CharSequence[size]);
		new AlertDialog.Builder(getActivity()).setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
			mShops.get(which).checked = isChecked;
		}).setPositiveButton(android.R.string.ok, (dialog, which) -> {
			updateSelectShopItemTitle();
			mDefaultPrefs.shops().put(Utils.toJson(mShops));
		}).show();
	}

	@OptionsItem(R.id.action_select_warehouses)
	void selectionWarehouses() {
		List<Boolean> listCheckedItem = StreamSupport.stream(mWarehouses).map(w -> w.checked).collect(Collectors.toList());
		boolean[] checkedItems = Booleans.toArray(listCheckedItem);
		CharSequence[] names = StreamSupport.stream(mWarehouses).map(w -> w.name).toArray(size -> new CharSequence[size]);
		new AlertDialog.Builder(getActivity()).setMultiChoiceItems(names, checkedItems, (dialog, which, isChecked) -> {
			mWarehouses.get(which).checked = isChecked;
		}).setPositiveButton(android.R.string.ok, (dialog, which) -> {
			updateSelectWarehouseItemTitle();
			mDefaultPrefs.warehouses().put(Utils.toJson(mWarehouses));
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
	void initRequest() {
		mResult = null;
		showProgress(true);
		mRequestHelper.queryDaySales(mStartDateButton.getText().toString(), mEndDateButton.getText().toString(), 0);
	}


	public void onEvent(DaySalesResult result) {
		showProgress(false);
		if (mResult == null) {
			mResult = result;
			initList(mResult);
		} else {
			mResult.stat_sales_sell_list.addAll(result.stat_sales_sell_list);
			BaseAdapter adapter = (BaseAdapter) mDaySalesList.getAdapter();
			adapter.notifyDataSetChanged();

			adapter = (BaseAdapter) mDateList.getAdapter();
			adapter.notifyDataSetChanged();
		}
	}

	private void initList(DaySalesResult result) {
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
