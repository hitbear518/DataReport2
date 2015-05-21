package com.zsxj.datareport2.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by sen on 15-5-18.
 */
public class DateAdapter extends BaseListAdapter<Sales> {

	private String mPattern;
	private DateTimeFormatter mFormatter;

	public DateAdapter(List<Sales> data, String datePattern) {
		super(data);
		mPattern = datePattern;
		mFormatter = DateTimeFormat.forPattern(datePattern);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_cell, parent, false);
		}
		TextView dateLabel = (TextView) convertView;
		dateLabel.setText(mFormatter.parseLocalDate(mData.get(position).sales_date).toString(mPattern));
		return dateLabel;
	}
}
