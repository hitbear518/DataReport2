package com.zsxj.datareport2.ui.widget;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Wang Sen on 3/2/2015.
 * Last modified:
 * By:
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected List<T> mData;

	public BaseListAdapter(List<T> data) {
		mData = data;
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mData != null ? mData.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
