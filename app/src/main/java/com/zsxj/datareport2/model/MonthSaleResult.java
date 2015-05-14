package com.zsxj.datareport2.model;

import com.zsxj.datareport2.network.RequestType;

import java.util.List;

/**
 * Created by sen on 15-5-13.
 * Last Modified by
 */
public class MonthSaleResult extends HttpResult {

    @Override
    public RequestType getRequestType() {
        return RequestType.MONTH_SALES;
    }

    public List<Sales> stat_sales_sell_list;
}
