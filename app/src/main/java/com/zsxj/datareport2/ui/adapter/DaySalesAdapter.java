package com.zsxj.datareport2.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.zsxj.datareport2.model.Sales;
import com.zsxj.datareport2.ui.widget.DaySalesItemView;
import com.zsxj.datareport2.ui.widget.DaySalesItemView_;

import java.util.List;

/**
 * Created by sen on 15-5-18.
 */
public class DaySalesAdapter extends BaseListAdapter<Sales> {

	public DaySalesAdapter(List<Sales> data) {
		super(data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DaySalesItemView itemView;
		if (convertView == null) {
			itemView = DaySalesItemView_.build(parent.getContext());
		} else {
			itemView = (DaySalesItemView) convertView;
		}
		itemView.bind(mData.get(position));
		return itemView;
	}
}
