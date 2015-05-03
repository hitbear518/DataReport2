package com.zsxj.datareport2.ui.activity;

import android.support.v7.widget.Toolbar;

import com.zsxj.datareport2.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends HttpSubscriberActivity {

	@ViewById(R.id.toolbar)
	Toolbar mToolbar;

	@AfterViews
	void init() {
		setSupportActionBar(mToolbar);
	}
}
