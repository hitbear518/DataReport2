package com.zsxj.datareport2;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by sen on 15-5-4.
 * Last modified by
 */
public class DataReport2App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JodaTimeAndroid.init(this);
		Stetho.initialize(
			Stetho.newInitializerBuilder(this)
				.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
				.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
				.build());
	}
}
