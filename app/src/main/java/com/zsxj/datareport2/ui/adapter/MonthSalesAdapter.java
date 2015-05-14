package com.zsxj.datareport2.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;

import java.util.List;

/**
 * Created by sen on 15-5-13.
 * Last Modified by
 */
public class MonthSalesAdapter extends UltimateViewAdapter {

    private List<Sales> mListSales;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView infoLabel;
        public final TextView filterLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            infoLabel = (TextView) itemView.findViewById(R.id.info_label);
            filterLabel = (TextView) itemView.findViewById(R.id.filter_label);
        }
    }

    public MonthSalesAdapter(List<Sales> mListSales) {
        this.mListSales = mListSales;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_sales, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return mListSales.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= mListSales.size() : position < mListSales.size()) && (customHeaderView == null || position > 0)) {
            Sales sales = mListSales.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.infoLabel.setText(sales.sales_date + "/" + sales.shop_name + "/" + sales.warehouse_name);
            viewHolder.filterLabel.setText("新增：" + sales.new_trades + "/" + sales.new_trades_amount);
        }
    }


}
