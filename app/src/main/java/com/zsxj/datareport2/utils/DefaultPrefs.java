package com.zsxj.datareport2.utils;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by sen on 15-5-15.
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface DefaultPrefs {
	String warehouses();
}
