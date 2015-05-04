package com.zsxj.datareport2.model;

import com.zsxj.datareport2.network.RequestType;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public class LoginResult extends HttpResult {

	@Override
	public RequestType getRequestType() {
		return RequestType.LOGIN;
	}
}
