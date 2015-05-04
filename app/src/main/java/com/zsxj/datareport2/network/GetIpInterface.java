package com.zsxj.datareport2.network;

import com.zsxj.datareport2.model.IpResult;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Wang Sen on 1/27/2015.
 */
public interface GetIpInterface {
	@POST("/mobile/map.php?ua=mobile")
	public void getIp(@Query("sid") String sid, Callback<IpResult> ipResultCallback);
}
