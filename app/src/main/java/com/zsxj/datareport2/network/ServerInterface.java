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
public interface ServerInterface {
	public static final String WAREHOUSES = "warehouse_no";
	public static final String SPEC_NO = "spec_no";
	public static final String BARCODE = "barcode";
	public static final String TRANSFER_INFO = "transfer_info";
	public static final String TRANSFER_DETAIL_INFO = "transfer_detail_info";
	public static final String POSITION_NO = "position_no";

	@POST("/mobile/prepare.php")
	void getLicense(HttpCallback<LicenseResult> callback);

	@POST("/mobile/login.php")
	void login(@QueryMap Map<String, String> params, HttpCallback<LoginResult> callback);

	@POST("/mobile/warehouse.php?mine=1")
	void getWarehouses(HttpCallback<WarehouseResult> callback);

	@POST("/mobile/stat_sales_sell_query.php?type=1")
	public void queryDaySales(@QueryMap Map<String, String> params, HttpCallback<DaySalesResult> callback);

	@POST("/mobile/stat_sales_sell_query.php?type=2")
	public void queryMonthSales(@QueryMap Map<String, String> params, HttpCallback<MonthSaleResult> callback);

}
