package com.zsxj.datareport2.model;

import com.google.gson.annotations.SerializedName;
import com.zsxj.datareport2.network.RequestType;

/**
 * Created by Wang Sen on 1/27/2015.
 * Last modified:
 * By:
 */
public class LicenseResult extends HttpResult {
	@SerializedName("pk") public String publicKey;

	@Override
	public RequestType getRequestType() {
		return RequestType.LICENCE;
	}
}
