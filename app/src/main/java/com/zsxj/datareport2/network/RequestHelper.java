package com.zsxj.datareport2.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.event.BeforeRequestEvent;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;
import com.zsxj.datareport2.model.MonthSaleResult;
import com.zsxj.datareport2.model.Warehouse;
import com.zsxj.datareport2.model.WarehouseResult;
import com.zsxj.datareport2.utils.DefaultPrefs_;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by sen on 15-5-11.
 * Last Modified by
 */
@EBean(scope = EBean.Scope.Singleton)
public class RequestHelper {

    @Pref
    DefaultPrefs_ mDefaultPrefs;

    public void init(String host) {
        PdaInterfaceHolder.init(host);
    }

    public void queryLicense() {
        PdaInterfaceHolder.get().queryLicense(new HttpCallback<LicenseResult>());
    }

    public void login(String sid, String username, String timestamp, String sign) {
        Map<String, String> params = new HashMap<>();
        params.put(PdaInterface.SID, sid);
        params.put(PdaInterface.USERNAME, username);
        params.put(PdaInterface.TIMESTAMP, timestamp);
        params.put(PdaInterface.SIGN, sign);
        PdaInterfaceHolder.get().login(params, new HttpCallback<LoginResult>());
    }

    public void queryWarehouses() {
        PdaInterfaceHolder.get().queryWarehouses(new HttpCallback<WarehouseResult>());
    }

    public void queryDaySales(String startDate, String endDate) {
        EventBus.getDefault().post(new BeforeRequestEvent());

        String warehousesStr = mDefaultPrefs.warehouses().get();
        Gson gson = new Gson();
        List<Warehouse> warehouses = gson.fromJson(warehousesStr, new TypeToken<List<Warehouse>>() {
		}.getType());
		List<String> nos = StreamSupport.stream(warehouses).filter(warehouse -> warehouse.checked).map(warehouse -> warehouse.name).collect(Collectors.toList());
        Map<String, String> params = new HashMap<>();
        params.put("start_time", startDate);
        params.put("end_time", endDate);
        params.put("warehouse_no_list", Utils.toJson(nos));
        HttpCallback<DaySalesResult> httpCallback = new HttpCallback<>();
        PdaInterfaceHolder.get().queryDaySales(params, httpCallback);
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

        PdaInterfaceHolder.get().queryMonthSales(params, new HttpCallback<MonthSaleResult>());
    }
}
