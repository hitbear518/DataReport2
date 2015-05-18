package com.zsxj.datareport2.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.ui.fragment.DaySalesFragment_;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
public class SalesFragmentPagerAdapter extends FragmentPagerAdapter {

	private Context mContext;

	public SalesFragmentPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return DaySalesFragment_.builder().build();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return mContext.getString(R.string.title_day_sales);
		case 1:
			return mContext.getString(R.string.title_month_sales);
		default:
			return null;
		}
	}
}
