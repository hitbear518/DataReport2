package com.zsxj.datareport2.network;

import com.zsxj.datareport2.model.DaySalesResult;
import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;
import com.zsxj.datareport2.model.MonthSaleResult;
import com.zsxj.datareport2.model.WarehouseResult;

import java.util.Map;

import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public interface PdaInterface {
	public static final String SID = "sid";
	public static final String USERNAME = "nick";
	public static final String TIMESTAMP = "timestamp";
	public static final String SIGN = "sign";
	public static final String WAREHOUSES = "warehouses";

	@POST("/mobile/prepare.php")
	void queryLicense(HttpCallback<LicenseResult> callback);

	@POST("/mobile/login.php")
	void login(@QueryMap Map<String, String> params, HttpCallback<LoginResult> callback);

	@POST("/mobile/warehouse.php?mine=1")
	void queryWarehouses(HttpCallback<WarehouseResult> callback);

	@POST("/mobile/stat_sales_sell_query.php?type=1")
	public void queryDaySales(@QueryMap Map<String, String> params, HttpCallback<DaySalesResult> callback);

	@POST("/mobile/stat_sales_sell_query.php?type=2")
	public void queryMonthSales(@QueryMap Map<String, String> params, HttpCallback<MonthSaleResult> callback);

}
