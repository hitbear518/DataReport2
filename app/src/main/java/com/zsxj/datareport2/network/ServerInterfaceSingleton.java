package com.zsxj.datareport2.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.zsxj.datareport2.R;

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
public class ServerInterfaceSingleton {

	private static ServerInterface sInstance;

	public static void reset() {
		sInstance = null;
	}

	public static ServerInterface get(Context context) {
		if (sInstance == null) {

			// Setup session cookie manager.
			CookieManager cm = new CookieManager();
			CookieHandler.setDefault(cm);

			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			String host = prefs.getString(context.getString(R.string.pref_key_host), null);
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
				sInstance = restAdapter.create(ServerInterface.class);
				
			}
		}

		return sInstance;
	}
}
