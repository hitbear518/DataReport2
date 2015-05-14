package com.zsxj.datareport2.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.Sales;

import java.util.List;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    private List<Sales> mListSales;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView filterLabel;
        final TextView infoLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext().getApplicationContext(), "test", Toast.LENGTH_SHORT).show();

                }
            });

            filterLabel = (TextView) itemView.findViewById(R.id.filter_label);
            infoLabel = (TextView) itemView.findViewById(R.id.info_label);
        }
    }

    public SalesAdapter(List<Sales> listSales) {
        this.mListSales = listSales;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sales, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sales sales = mListSales.get(position);
        holder.filterLabel.setText(sales.sales_date + "/" + sales.shop_name + "/" + sales.warehouse_name);
        holder.infoLabel.setText("新增：" + sales.new_trades + "/" + sales.new_trades_amount);
    }

    @Override
    public int getItemCount() {
        return mListSales.size();
    }

}
