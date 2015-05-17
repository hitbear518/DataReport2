package com.zsxj.datareport2.ui.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melnykov.fab.FloatingActionButton;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.ReturnEndDateEvent;
import com.zsxj.datareport2.event.ReturnStartDateEvent;
import com.zsxj.datareport2.event.SelectEndDateEvent;
import com.zsxj.datareport2.event.SelectStartDateEvent;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.Warehouse;
import com.zsxj.datareport2.network.PdaInterface;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.ui.adapter.SalesAdapter;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.joda.time.LocalDate;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
@EFragment(R.layout.fragment_day_sales)
@OptionsMenu(R.menu.menu_day_sales)
public class DaySalesFragment extends BaseFragment {

    @ViewById(R.id.start_date_button)
    Button mStartDateButton;
    @ViewById(R.id.end_date_button)
    Button mEndDateButton;
//    @ViewById(R.id.recycler_view)
//    RecyclerView mRecyclerView;
    @ViewById(R.id.fab)
    FloatingActionButton mFab;

    @InstanceState
    String mStartDate;
    @InstanceState
    String mEndDate;

    @InstanceState
    DaySalesResult mResult;

    @OptionsMenuItem(R.id.action_select_warehouses)
    MenuItem mSelectWarehouseMenuItem;

    @Bean
    RequestHelper mRequestHelper;

    @ViewById(R.id.day_sales_list)
    ListView mDaySalesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterViews
    void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.setHasFixedSize(true);
//        mFab.attachToRecyclerView(mRecyclerView);

        if (mStartDate == null) {
            mStartDate = "2015-03-01";
        }
        if (mEndDate == null) {
            mEndDate = "2015-05-01";
        }
        mStartDateButton.setText(mStartDate);
        mEndDateButton.setText(mEndDate);

        if (mResult == null) {
            mRequestHelper.queryDaySales(mStartDate, mEndDate);
        } else {
            setList();
        }

    }

    @Click(R.id.start_date_button)
    void startTimeButtonClicked() {
        LocalDate now = new LocalDate();
        LocalDate twoMonthAgo = now.minusMonths(3);

        EventBus.getDefault().postSticky(new SelectStartDateEvent(twoMonthAgo.toDate(), now.toDate()));
    }

    @Click(R.id.end_date_button)
    void endTimeButtonClicked() {
        LocalDate now = new LocalDate();
        LocalDate twoMonthAgo = now.minusMonths(2);

        EventBus.getDefault().postSticky(new SelectEndDateEvent(twoMonthAgo.toDate(), now.toDate()));
    }

    public void onEventMainThread(DaySalesResult daySalesResult) {
        EventBus.getDefault().removeStickyEvent(daySalesResult);
        mResult = daySalesResult;
        setList();
    }

    private void setList() {
        SalesAdapter salesAdapter = new SalesAdapter(mResult.stat_sales_sell_list);
//        mRecyclerView.setAdapter(salesAdapter);
    }

    public void onEventMainThread(ReturnStartDateEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        mStartDate = event.date;
        mStartDateButton.setText(event.date);
    }

    public void onEventMainThread(ReturnEndDateEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        mEndDate = event.date;
        mEndDateButton.setText(event.date);
    }

    @Click(R.id.fab)
    void fabClicked() {
       mRequestHelper.queryDaySales(mStartDate, mEndDate);
    }

    @OptionsItem(R.id.action_select_warehouses)
    void selectWarehouses() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String warehousesStr = prefs.getString(PdaInterface.WAREHOUSES, null);
        Gson gson = new Gson();
        final List<Warehouse> warehouses = gson.fromJson(warehousesStr, new TypeToken<List<Warehouse>>() {
        }.getType());
        CharSequence[] nos = new CharSequence[warehouses.size()];
        final boolean[] checkedItems = new boolean[warehouses.size()];
        for (int i = 0; i < nos.length; i++) {
            nos[i] = warehouses.get(i).name;
            checkedItems[i] = warehouses.get(i).checked;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMultiChoiceItems(nos, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        warehouses.get(which).checked = isChecked;
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int count = 0;
                        for (Warehouse warehouse : warehouses) {
                            if (warehouse.checked) {
                                count++;
                            }
                        }

                        mSelectWarehouseMenuItem.setTitle("仓库(" + count + ")");
                        prefs.edit().putString(PdaInterface.WAREHOUSES, Utils.toJson(warehouses)).apply();

                    }
                });
        builder.show();
    }
}
