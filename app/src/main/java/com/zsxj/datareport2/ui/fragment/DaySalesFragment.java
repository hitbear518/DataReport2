package com.zsxj.datareport2.ui.fragment;

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
@EFragment(R.layout.fragment_day_sales)
@OptionsMenu(R.menu.menu_day_sales)
public class DaySalesFragment extends BaseFragment {

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

	@OptionsMenuItem(R.id.action_select_shop)
	MenuItem mSelectShopItem;
	@OptionsMenuItem(R.id.action_select_warehouses)
	MenuItem mSelectWarehouseMenuItem;

	@Pref
	DefaultPrefs_ mDefaultPrefs;

	@Bean
	RequestHelper mRequestHelper;


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

//		mDaySalesList.setOnScrollListener(new EndlessScrollListener(mDateList) {
//			@Override
//			public void onLoadMore() {
//				if (mResult.stat_sales_sell_list.size() < mResult.total_count) {
//					int pageNo = mResult.stat_sales_sell_list.size() / RequestHelper.CURRENT_PAGE_SIZE;
//					String startDate = mStartDateButton.getText().toString();
//					String endDate = mEndDateButton.getText().toString();
//					mRequestHelper.queryDaySales(startDate, endDate, pageNo);
//					showProgress(true);
//				}
//			}
//		});
		mDateList.setOnScrollListener(scroll2);
		mDaySalesList.setOnScrollListener(scroll1);
	}

	AbsListView.OnScrollListener scroll1 = new AbsListView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (view.getChildCount() > 0) {
				int current = mDateList.getScrollY();
				int target = firstVisibleItem * view.getChildAt(0).getHeight() + view.getChildAt(0).getTop();
				if (current != target) {
					mDateList.post(() -> {
						mDateList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
					});
				}
			}
		}
	};

	AbsListView.OnScrollListener scroll2 = new AbsListView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (view.getChildCount() > 0) {
				int current = mDaySalesList.getScrollY();
				int target = firstVisibleItem * view.getChildAt(0).getHeight() + view.getChildAt(0).getTop();
				if (current != target) {
					mDaySalesList.post(() -> {
						mDaySalesList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
					});
				}
			}
		}
	};

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
		LocalDate initialDate = Utils.DAY_FORMATTER.parseLocalDate(mStartDateButton.getText().toString());
		DatePickerFragment_.builder()
			.mInitialDate(initialDate)
			.mEvent(new ReturnStartDateEvent())
			.build()
			.show(getFragmentManager(), null);
	}

	@Click(R.id.end_date_button)
	void endDateClicked() {
		LocalDate initialDate = Utils.DAY_FORMATTER.parseLocalDate(mEndDateButton.getText().toString());
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

	public void onEventMainThread(DaySalesResult result) {
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
		DaySalesAdapter adapter = new DaySalesAdapter(result.stat_sales_sell_list);
		mDaySalesList.setAdapter(adapter);
		mDateList.setAdapter(new DateAdapter(result.stat_sales_sell_list, Utils.DAY_PATTERN));
	}

	public void onEventMainThread(ReturnStartDateEvent event) {
		mStartDateButton.setText(event.date);
	}

	public void onEventMainThread(ReturnEndDateEvent event) {
		mEndDateButton.setText(event.date);
	}
}
