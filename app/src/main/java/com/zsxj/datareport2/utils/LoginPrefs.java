package com.zsxj.datareport2.utils;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by sen on 15-5-4.
 */
@SharedPref(value = SharedPref.Scope.ACTIVITY_DEFAULT)
public interface LoginPrefs {

	String sid();

	String username();

	String lastLogin();
}
