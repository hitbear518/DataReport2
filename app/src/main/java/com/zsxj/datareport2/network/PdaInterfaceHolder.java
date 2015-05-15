package com.zsxj.datareport2.network;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Wang Sen on 1/30/2015.
 * Last modified:
 * By:
 */
public class PdaInterfaceHolder {

	private static PdaInterface sInstance;

	public static void init(String host) {
		// Setup session cookie manager.
		CookieManager cm = new CookieManager();
		CookieHandler.setDefault(cm);

		if (host != null) {
			OkHttpClient client = new OkHttpClient();
			client.networkInterceptors().add(new StethoInterceptor());
			client.setConnectTimeout(8, TimeUnit.SECONDS);
			client.setWriteTimeout(8, TimeUnit.SECONDS);
			client.setReadTimeout(8, TimeUnit.SECONDS);
			RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://" + host)
				.setClient(new OkClient(client))
				.build();
			sInstance = restAdapter.create(PdaInterface.class);
		}
	}

	public static PdaInterface get() {
		if (sInstance == null) Logger.e("PdaInterface hasn't been initialized");

		return sInstance;
	}
}
