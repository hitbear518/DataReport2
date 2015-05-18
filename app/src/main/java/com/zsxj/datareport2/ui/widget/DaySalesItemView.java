package com.zsxj.datareport2.ui.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by sen on 15-5-18.
 */
@EViewGroup(R.layout.list_item_day_sales)
public class DaySalesItemView extends LinearLayout {

	@ViewById(R.id.date_label)
	TextView mDateLabel;
	@ViewById(R.id.shop_label)
	TextView mShopLabel;
	@ViewById(R.id.warehouse_label)
	TextView mWarehouseLabel;
	@ViewById(R.id.new_trades_label)
	TextView mNewTradesLabel;
	@ViewById(R.id.new_trades_amount_label)
	TextView mNewTradesAmountLabel;
	@ViewById(R.id.check_trades_label)
	TextView mCheckTradesLabel;
	@ViewById(R.id.check_trades_amount_label)
	TextView mCheckTradesAmountLabel;

	public DaySalesItemView(Context context) {
		super(context);
	}

	public void bind(Sales sales) {
		mDateLabel.setText(sales.sales_date);
		mShopLabel.setText(sales.shop_name);
		mWarehouseLabel.setText(sales.warehouse_name);
		mNewTradesLabel.setText(String.valueOf(sales.new_trades));
		mNewTradesAmountLabel.setText(sales.new_trades_amount);
		mCheckTradesLabel.setText(String.valueOf(sales.check_trades));
		mCheckTradesAmountLabel.setText(sales.check_trades_amount);
	}
}
