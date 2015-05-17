package com.zsxj.datareport2.ui.fragment;

import android.widget.Button;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.network.RequestHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by sen on 15-5-17.
 * Last Modified by
 */
@EFragment(R.layout.fragment_day_sales3)
public class DaySalesFragment2 extends BaseFragment {


    @ViewById(R.id.start_date_button)
    Button mStartDateButton;
    @ViewById(R.id.end_date_button)
    Button mEndDateButton;
    @ViewById(R.id.day_sales_list)
    ListView mDaySalesList;
    @ViewById(R.id.fab)
    FloatingActionButton mFab;

    @Bean
    RequestHelper mRequestHelper;

    String mStartDate;
    String mEndDate;
    DaySalesResult mResult;

    @AfterViews
    void init() {
        if (mStartDate == null) mStartDate = "2015-03-01";
        if (mEndDate == null) mEndDate = "2015-05-01";
        if (mResult == null) {
            mRequestHelper.queryDaySales(mStartDate, mEndDate);
        } else {

        }
    }

    public void onEvent(DaySalesResult result) {
        mResult = result;
    }
}
