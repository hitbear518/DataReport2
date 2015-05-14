package com.zsxj.datareport2.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

/**
 * Created by sen on 15-5-14.
 * Last Modified by
 */
@ParcelablePlease
public class SelectDateEvent implements Parcelable {

    public Date min;

    public SelectDateEvent() {
    }

    public Date max;

    public SelectDateEvent(Date min, Date max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SelectDateEventParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<SelectDateEvent> CREATOR = new Creator<SelectDateEvent>() {
        public SelectDateEvent createFromParcel(Parcel source) {
            SelectDateEvent target = new SelectDateEvent();
            SelectDateEventParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public SelectDateEvent[] newArray(int size) {
            return new SelectDateEvent[size];
        }
    };
}
