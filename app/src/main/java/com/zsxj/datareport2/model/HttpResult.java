package com.zsxj.datareport2.model;


import com.zsxj.datareport2.network.RequestType;

/**
 * Created by hitbe_000 on 1/27/2015.
 */
public abstract class HttpResult {
	public int code;
	public String message;

	public RequestType getRequestType() {
		return RequestType.UNDEFINED;
	}
}
