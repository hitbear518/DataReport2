package com.zsxj.datareport2.model;

import com.zsxj.datareport2.network.RequestType;

/**
 * Created by hitbe_000 on 1/27/2015.
 */
public class IpResult extends HttpResult {
	public String ip;

	@Override
	public RequestType getRequestType() {
		return RequestType.IP;
	}
}
