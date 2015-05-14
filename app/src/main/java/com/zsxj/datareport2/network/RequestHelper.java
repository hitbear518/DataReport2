package com.zsxj.datareport2.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.MonthSaleResult;
import com.zsxj.datareport2.model.Warehouse;
import com.zsxj.datareport2.utils.Utils;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sen on 15-5-11.
 * Last Modified by
 */
public class RequestHelper {

    private static RequestHelper sInstance;

    public static RequestHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestHelper(context);
        }
        return sInstance;
    }

    private Context mContext;
    private ServerInterface mServerInterface;

    public RequestHelper(Context context) {
        mContext = context.getApplicationContext();
        mServerInterface = ServerInterfaceSingleton.get(context);
    }

    public void queryDaySales(String startDate, String endDate) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        String warehousesStr = prefs.getString(ServerInterface.WAREHOUSES, null);
        Gson gson = new Gson();
        final List<Warehouse> warehouses = gson.fromJson(warehousesStr, new TypeToken<List<Warehouse>>() {
        }.getType());
        List<String> nos = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.checked) {
                nos.add(warehouse.warehouseNo);
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("start_time", startDate);
        params.put("end_time", endDate);
        params.put("warehouse_no_list", Utils.toJson(nos));
        HttpCallback<DaySalesResult> httpCallback = new HttpCallback<>();
        mServerInterface.queryDaySales(params, httpCallback);
    }

    public void queryMonthales() {
        Map<String, String> params = new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
        LocalDateTime end = fmt.parseLocalDateTime("2015-05-01 00:00:00");
        LocalDateTime start = end.minusMonths(5);
        Logger.d("end: " + end.toString("YYYY-MM-dd HH:mm:ss"));
        Logger.d("start: " + start.toString("YYYY-MM-dd HH:mm:ss"));
        params.put("start_time", start.toString("YYYY-MM-dd HH:mm:ss"));
        params.put("end_time", end.toString("YYYY-MM-dd HH:mm:ss"));

        mServerInterface.queryMonthSales(params, new HttpCallback<MonthSaleResult>());
    }
}
