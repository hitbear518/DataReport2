package com.zsxj.datareport2.network;

import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.event.HttpErrorEvent;
import com.zsxj.datareport2.model.HttpResult;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Wang Sen on 2/2/2015.
 * Last modified:
 * By:
 */
public class HttpCallback<T extends HttpResult> implements Callback<T> {

	@Override
	public void success(T result, Response response) {
		if (result.code == 0) {
			EventBus.getDefault().postSticky(result);
		} else {
			Logger.e("Incorrect HttpResult: code=" + result.code + ", message=" + result.message);
			Logger.e("URL: " + response.getUrl());
			EventBus.getDefault().postSticky(new HttpErrorEvent(result.message));
		}
	}

	@Override
	public void failure(RetrofitError error) {
		Logger.e("Retrofit error: " + error.getMessage(), error);
		Logger.e("URL: " + error.getUrl());
		EventBus.getDefault().postSticky(new HttpErrorEvent(error.getMessage()));
	}
}
