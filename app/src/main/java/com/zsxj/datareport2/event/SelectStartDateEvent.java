package com.zsxj.datareport2.event;

import java.util.Date;

/**
 * Created by sen on 15-5-14.
 * Last Modified by
 */
public class SelectStartDateEvent extends SelectDateEvent {
    public SelectStartDateEvent(Date min, Date max) {
        super(min, max);
    }
}
