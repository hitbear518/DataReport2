package com.zsxj.datareport2.network;

import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;
import com.zsxj.datareport2.model.MonthSaleResult;
import com.zsxj.datareport2.model.Shop;
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

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by sen on 15-5-11.
 * Last Modified by
 */
@EBean(scope = EBean.Scope.Singleton)
public class RequestHelper {

	public static int sPageNo;
	public static final int CURRENT_PAGE_SIZE = 14;

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

    public void queryShops() {
        PdaInterfaceHolder.get().queryShops(new HttpCallback<>());
    }

    public void queryWarehouses() {
        PdaInterfaceHolder.get().queryWarehouses(new HttpCallback<WarehouseResult>());
    }

    public void queryDaySales(String startDate, String endDate, int pageNo) {
        Map<String, String> params = new HashMap<>();
        params.put(PdaInterface.START_TIME, startDate);
        params.put(PdaInterface.END_TIME, endDate);

        String warehousesStr = mDefaultPrefs.warehouses().get();
        List<Warehouse> warehouses = Utils.toList(warehousesStr, Warehouse.class);
        List<String> warehouseNos = StreamSupport.stream(warehouses).filter(warehouse -> warehouse.checked).map(warehouse -> warehouse.warehouseNo).collect(Collectors.toList());
        params.put(PdaInterface.WAREHOUSE_NO_LIST, Utils.toJson(warehouseNos));

        String shopsStr = mDefaultPrefs.shops().get();
        List<Shop> shops = Utils.toList(shopsStr, Shop.class);
        List<String> shopNos = StreamSupport.stream(shops).filter(shop -> shop.checked).map(shop -> shop.shop_no).collect(Collectors.toList());
        params.put(PdaInterface.SHOP_NO_LIST, Utils.toJson(shopNos));

		params.put(PdaInterface.PAGE_NO, String.valueOf(pageNo));
		params.put(PdaInterface.PAGE_SIZE, String.valueOf(CURRENT_PAGE_SIZE));
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
