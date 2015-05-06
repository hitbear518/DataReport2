package com.zsxj.datareport2.network;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public interface TestInterface {
	@POST("/mobile/stock_detail.php?warehouse_no=WH001&spec_no=testpd0001")
	public void testStocks(@QueryMap Map<String, String> params, Callback<String> callback);

	@POST("/mobile/warehouse.php?type=abdcdd&mine=1&warehouse_no=asdfasdfasd")
	public void testWarehouses(Callback<String> callback);

	@POST("/mobile/map.php")
	public void testIp(@Query("sid") String sid, Callback<String> callback);

	@POST("/mobile/trade_goods_get_for_examine.php")
	public void testTradeGoods(@Query("query_no") String queryNo, Callback<String> callback);

	@POST("/mobile/stock_fast_pd_for_batch.php")
	public void testFastPdForBatch(@Query("pd_info") String pdInfo, Callback<String> callback);

	@POST("/mobile/stock_transfer.php")
	public void testStockTransfer(@QueryMap Map<String, String> params, Callback<String> callback);

	@POST("/mobile/get_goods_info_by_scan.php")
	public void testGetGoodsInfoByScan(@Query("query_no") String queryNo, Callback<String> callback);

	@POST("/mobile/warehouse_position_query.php")
	public void testWarehousePositionQuery(@QueryMap Map<String, String> params, Callback<String> callback);

	@POST("/mobile/stat_sales_sell_query.php")
	public void testSalesSell(@QueryMap Map<String, String> params, Callback<String> callback);

	@POST("/mobile/stat_sales_profit_report_query.php")
	public void testSalesProfit(@QueryMap Map<String, String> params, Callback<String> callback);
}