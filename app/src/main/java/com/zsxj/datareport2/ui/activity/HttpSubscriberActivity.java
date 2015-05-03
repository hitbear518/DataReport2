package com.zsxj.datareport2.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zsxj.datareport2.event.HttpErrorEvent;
import com.zsxj.datareport2.model.HttpResult;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-2.
 * Last Modified by
 */
public class HttpSubscriberActivity extends AppCompatActivity {

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.getDefault().registerSticky(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);
	}

	public void onEvent(HttpResult httpResult) {
		EventBus.getDefault().removeStickyEvent(httpResult);
	}

	public void onEvent(HttpErrorEvent httpErrorEvent) {
		EventBus.getDefault().removeStickyEvent(httpErrorEvent);
		Toast.makeText(getApplicationContext(), httpErrorEvent.errMsg, Toast.LENGTH_LONG).show();
	}
}
