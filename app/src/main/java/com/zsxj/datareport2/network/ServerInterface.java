package com.zsxj.datareport2.network;

import com.zsxj.datareport2.model.LicenseResult;
import com.zsxj.datareport2.model.LoginResult;

import java.util.Map;

import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public interface ServerInterface {
	public static final String WAREHOUSE_NO = "warehouse_no";
	public static final String SPEC_NO = "spec_no";
	public static final String BARCODE = "barcode";
	public static final String TRANSFER_INFO = "transfer_info";
	public static final String TRANSFER_DETAIL_INFO = "transfer_detail_info";
	public static final String POSITION_NO = "position_no";

	@POST("/mobile/prepare.php")
	void getLicense(HttpCallback<LicenseResult> callback);

	@POST("/mobile/login.php")
	void login(@QueryMap Map<String, String> params, HttpCallback<LoginResult> callback);

//	@POST("/mobile/warehouse.php?mine=1")
//	void getWarehouses(HttpCallback<WarehouseResult> callback);
//
//	@POST("/mobile/stock_detail.php")
//	public void getStocks(@QueryMap Map<String, String> params, HttpCallback<StocksResult> callback);
//
//	@POST("/mobile/stock_fast_pd_for_batch.php")
//	public void fastPdForBatch(@Query("pd_info") String pdInfo, HttpCallback<FastPdResult> callback);
//
//	@POST("/mobile/trade_goods_get_for_examine.php")
//	public void getTradeGoods(@Query("query_no") String queryNo, HttpCallback<TradeGoodsListResult> callback);
//
//	@POST("/mobile/stock_transfer.php")
//	public void stockTransfer(@QueryMap Map<String, String> params, HttpCallback<TransferResult> callback);
//
//	@POST("/mobile/get_goods_info_by_scan.php")
//	public void queryInspectionGoods(@Query("query_no") String queryNo, HttpCallback<InspectionGoodsListResult> callback);
//
//	@POST("/mobile/stock_sales_inspection.php")
//	public void stockSalesInspection(@Query("inspection_info") String inspectionInfo, HttpCallback<ConfirmInspectionResult> callback);
//
//	@POST("/mobile/warehouse_position_query.php")
//	public void warehousePositionQuery(@QueryMap Map<String, String> params, HttpCallback<PositionsResult> callback);
}
