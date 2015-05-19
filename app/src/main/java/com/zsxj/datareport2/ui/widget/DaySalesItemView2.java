package com.zsxj.datareport2.ui.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

/**
 * Created by sen on 15-5-18.
 */
@EViewGroup(R.layout.list_item_day_sales)
public class DaySalesItemView2 extends LinearLayout {
	@ViewsById({R.id.shop_label, R.id.warehouse_label, R.id.new_trades_label, R.id.new_trades_amount_label,
		R.id.check_trades_label, R.id.check_trades_amount_label,
		R.id.send_trades_label, R.id.send_trades_amount_label,
		R.id.send_unknown_goods_amount_label, R.id.send_goods_cost_label, R.id.commission_label,
		R.id.send_trades_profit_label, R.id.post_amount_label, R.id.post_cost_label,
		R.id.post_profit_label, R.id.package_cost_label
	})
	List<TextView> mLabels;

	public DaySalesItemView2(Context context) {
		super(context);
	}

	public void bind(Sales sales) {
//
//		for (int i = 0; i <fields.length; i++) {
//			mLabels.get(i).setText(String.valueOf(fields[i].get));
//		}
	}
}
