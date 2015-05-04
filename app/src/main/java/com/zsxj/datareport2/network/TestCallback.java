package com.zsxj.datareport2.network;

import android.util.Log;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Wang Sen on 2/6/2015.
 * Last modified:
 * By:
 */
public class TestCallback implements Callback<String> {
	private static final String TAG = "TestCallback";
	private TextView mTextView;

	public TestCallback(TextView textView) {
		mTextView = textView;
	}

	@Override
	public void success(String s, Response response) {

		mTextView.setText(s);
	}

	@Override
	public void failure(RetrofitError error) {
		Log.e(TAG, error.getMessage(), error);
		mTextView.setText(error.getMessage());
	}
}
