package com.zsxj.datareport2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.zsxj.datareport2.network.RequestType;

import java.util.List;

/**
 * Created by sen on 15-5-12.
 * Last Modified by
 */
@ParcelablePlease
public class DaySalesResult extends HttpResult implements Parcelable {

    public int total_count;

    public List<Sales> stat_sales_sell_list = null;

    @Override
    public RequestType getRequestType() {
        return RequestType.DAY_SALES;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        DaySalesResultParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<DaySalesResult> CREATOR = new Creator<DaySalesResult>() {
        public DaySalesResult createFromParcel(Parcel source) {
            DaySalesResult target = new DaySalesResult();
            DaySalesResultParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public DaySalesResult[] newArray(int size) {
            return new DaySalesResult[size];
        }
    };
}
