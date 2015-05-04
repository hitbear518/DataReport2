package com.zsxj.datareport2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.network.TestCallback;
import com.zsxj.datareport2.network.TestInterface;
import com.zsxj.datareport2.network.TestSingleton;

import org.joda.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wang Sen on 2/9/2015.
 * Last modified:
 * By:
 */
public class TestActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		TextView text = (TextView) findViewById(R.id.text);
		TestCallback callback = new TestCallback(text);

		TestInterface testInterface = TestSingleton.getInterface(this);
		Map<String, String> params = new HashMap<>();
		LocalDateTime now = new LocalDateTime();
		LocalDateTime weekAgo = now.minusWeeks(1);
		Logger.d("now: " + now.toString("YYYY-MM-dd HH:mm:ss"));
		Logger.d("weekAge: " + weekAgo.toString("YYYY-MM-dd HH:mm:ss"));
		params.put("start_time", weekAgo.toString("YYYY-MM-dd HH:mm:ss"));
		params.put("end_time", now.toString("YYYY-MM-dd HH:mm:ss"));
		params.put("type", "1");

		testInterface.testSalesSell(params, callback);
	}
}
