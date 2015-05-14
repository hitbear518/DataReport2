package com.zsxj.datareport2.model;

import com.zsxj.datareport2.network.RequestType;

import java.util.List;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public class WarehouseResult extends HttpResult {

	@Override
	public RequestType getRequestType() {
		return RequestType.WAREHOUSE;
	}

	public List<Warehouse> warehouses;
}
