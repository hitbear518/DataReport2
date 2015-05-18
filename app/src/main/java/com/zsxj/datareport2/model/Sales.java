package com.zsxj.datareport2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
@ParcelablePlease
public class Sales implements Parcelable {

    public String sales_date;
    public String shop_name;
    public String warehouse_name;
    public int new_trades;
    public String new_trades_amount;
    public int check_trades;
    public String check_trades_amount;
    public int send_trades;
    public String send_trades_amount;
    public String send_unknown_goods_amount;
    public String send_trade_profit;
    public String post_amount;
    public String post_cost;
    public String commission;
    public String package_cost;
    public String send_goods_cost;
    public String post_profit;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SalesParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Sales> CREATOR = new Creator<Sales>() {
        public Sales createFromParcel(Parcel source) {
            Sales target = new Sales();
            SalesParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Sales[] newArray(int size) {
            return new Sales[size];
        }
    };
}
