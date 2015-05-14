package com.zsxj.datareport2.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.network.RequestType;
import com.zsxj.datareport2.ui.adapter.SalesFragmentPagerAdapter;
import com.zsxj.datareport2.ui.widget.SlidingTabLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-11.
 * Last Modified by
 */
@EFragment(R.layout.fragment_pager)
public class SalesFragment extends Fragment {

    @ViewById(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabs;
    @ViewById(R.id.view_pager)
    ViewPager mViewPager;

    @AfterViews
    void init() {
        mViewPager.setAdapter(new SalesFragmentPagerAdapter(getActivity(), getChildFragmentManager()));
        mSlidingTabs.setViewPager(mViewPager);

        EventBus.getDefault().post(RequestType.DAY_SALES);
    }
}
