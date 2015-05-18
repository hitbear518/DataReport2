package com.zsxj.datareport2.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by sen on 15-5-14.
 * Last Modified by
 */
@ParcelablePlease
public class ReturnDateEvent implements Parcelable {
    public String date;

    public ReturnDateEvent() {
    }

    public ReturnDateEvent(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ReturnDateEventParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<ReturnDateEvent> CREATOR = new Creator<ReturnDateEvent>() {
        public ReturnDateEvent createFromParcel(Parcel source) {
            ReturnDateEvent target = new ReturnDateEvent();
            ReturnDateEventParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public ReturnDateEvent[] newArray(int size) {
            return new ReturnDateEvent[size];
        }
    };
}
