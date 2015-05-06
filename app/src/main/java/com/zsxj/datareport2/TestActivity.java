package com.zsxj.datareport2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zsxj.datareport2.network.TestCallback;
import com.zsxj.datareport2.network.TestInterface;
import com.zsxj.datareport2.network.TestSingleton;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		LocalDateTime now = fmt.parseLocalDateTime("2015-04-01 00:00:00");
		LocalDateTime start = now.minusMonths(1);
		Logger.d("now: " + now.toString("YYYY-MM-dd HH:mm:ss"));
		Logger.d("start: " + start.toString("YYYY-MM-dd HH:mm:ss"));
		params.put("start_time", start.toString("YYYY-MM-dd HH:mm:ss"));
		params.put("end_time", now.toString("YYYY-MM-dd HH:mm:ss"));
//		params.put("type", "2");

//		testInterface.testSalesSell(params, callback);
		testInterface.testSalesProfit(params, callback);
	}
}
