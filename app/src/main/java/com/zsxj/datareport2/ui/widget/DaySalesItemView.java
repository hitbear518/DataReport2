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
	@ViewById(R.id.send_trades_label)
	TextView mSendTradesLabel;
	@ViewById(R.id.send_trades_amount_label)
	TextView mSendTradesAmountLabel;
	@ViewById(R.id.send_unknown_goods_amount_label)
	TextView mSendUnknownGoodsAmount;
	@ViewById(R.id.send_goods_cost_label)
	TextView mSendGoodsCostLabel;
	@ViewById(R.id.commission_label)
	TextView mCommissionLabel;
	@ViewById(R.id.send_trades_profit_label)
	TextView mSendTradesProfitLabel;
	@ViewById(R.id.post_amount_label)
	TextView mPostAmountLabel;
	@ViewById(R.id.post_cost_label)
	TextView mPostCostLabel;
	@ViewById(R.id.post_profit_label)
	TextView mPostProfitLabel;

	public DaySalesItemView(Context context) {
		super(context);
	}

	public void bind(Sales sales) {
		mShopLabel.setText(sales.shop_name);
		mWarehouseLabel.setText(sales.warehouse_name);
		mNewTradesLabel.setText(String.valueOf(sales.new_trades));
		mNewTradesAmountLabel.setText(sales.new_trades_amount);
		mCheckTradesLabel.setText(String.valueOf(sales.check_trades));
		mCheckTradesAmountLabel.setText(sales.check_trades_amount);
		mSendTradesLabel.setText(String.valueOf(sales.send_trades));
		mSendTradesAmountLabel.setText(sales.send_trades_amount);
		mSendUnknownGoodsAmount.setText(sales.send_unknown_goods_amount);
		mSendGoodsCostLabel.setText(sales.send_goods_cost);
		mCommissionLabel.setText(sales.commission);
		mSendTradesProfitLabel.setText(sales.send_trade_profit);
		mPostAmountLabel.setText(sales.post_amount);
		mPostCostLabel.setText(sales.post_cost);
		mPostProfitLabel.setText(sales.post_profit);
	}
}
