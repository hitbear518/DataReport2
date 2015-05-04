package com.zsxj.datareport2.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.zsxj.datareport2.R;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Wang Sen on 2/6/2015.
 * Last modified:
 * By:
 */
public class TestSingleton {
	public static TestInterface getIpInterface(Context context) {
		return new RestAdapter.Builder()
				.setEndpoint("http://erp.wangdian.cn")
				.setConverter(new StringConverter())
				.build().create(TestInterface.class);
	}

	public static TestInterface getInterface(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		String host = prefs.getString(context.getString(R.string.pref_key_host), null);
		OkHttpClient client = new OkHttpClient();
		client.networkInterceptors().add(new StethoInterceptor());
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://" + host)
				.setConverter(new StringConverter())
				.setClient(new OkClient(client))
				.build();
		return restAdapter.create(TestInterface.class);
	}
}
