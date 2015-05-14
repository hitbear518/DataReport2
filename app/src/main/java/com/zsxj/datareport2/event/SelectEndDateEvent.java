package com.zsxj.datareport2.event;

import java.util.Date;

/**
 * Created by sen on 15-5-14.
 * Last Modified by
 */
public class SelectEndDateEvent extends SelectDateEvent {
    public SelectEndDateEvent(Date min, Date max) {
        super(min, max);
    }
}
