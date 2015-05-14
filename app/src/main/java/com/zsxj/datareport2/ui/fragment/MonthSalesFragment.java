package com.zsxj.datareport2.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.MonthSaleResult;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.ui.adapter.MonthSalesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
@EFragment(R.layout.fragment_month_sales)
public class MonthSalesFragment extends BaseFragment {

    @ViewById(R.id.start_time_button)
    Button mStartTimeButton;
    @ViewById(R.id.end_time_button)
    Button mEndTimeButton;
    @ViewById(R.id.ultimate_recycler_view)
    UltimateRecyclerView mUltimateRecyclerView;

    @AfterViews
    void init() {
//        mUltimateRecyclerView.enableLoadmore();
        mUltimateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUltimateRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RequestHelper.getInstance(getActivity()).queryMonthales();
    }

    @Click(R.id.start_time_button)
    void startTimeButtonClicked() {}

    @Click(R.id.end_time_button)
    void endTimeButtonClicked() {

    }

    public void onEventMainThread(MonthSaleResult monthSaleResult) {
        MonthSalesAdapter adapter = new MonthSalesAdapter(monthSaleResult.stat_sales_sell_list);
        mUltimateRecyclerView.setAdapter(adapter);
    }
}
