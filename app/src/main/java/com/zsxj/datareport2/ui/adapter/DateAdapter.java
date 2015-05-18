package com.zsxj.datareport2.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;
import com.zsxj.datareport2.ui.widget.BaseListAdapter;

import java.util.List;

/**
 * Created by sen on 15-5-18.
 */
public class DateAdapter extends BaseListAdapter<Sales> {

	public DateAdapter(List<Sales> data) {
		super(data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_cell, parent, false);
		}
		TextView dateLabel = (TextView) convertView;
		dateLabel.setText(mData.get(position).sales_date);
		return dateLabel;
	}
}
