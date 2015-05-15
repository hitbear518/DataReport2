package com.zsxj.datareport2.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.HttpErrorEvent;
import com.zsxj.datareport2.utils.Utils;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-9.
 * Last Modified by
 */
public abstract class BaseMainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

	private static final int TIME_INTERVAL = 2000;
	private long mBackPressedTime;

	protected ActionBarDrawerToggle mDrawerToggle;

	// Set up drawer toggle
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackStackChanged() {
		mDrawerToggle.setDrawerIndicatorEnabled(inRootFragment());
	}

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

	@Override
	public void onBackPressed() {
		// Implement "Press back again to exit"
		boolean needPressBackAgain = System.currentTimeMillis() - mBackPressedTime >= TIME_INTERVAL;
		if (inRootFragment() && needPressBackAgain) {
			Toast.makeText(getApplicationContext(), R.string.prompt_press_back_again_to_exit, Toast.LENGTH_SHORT).show();
			mBackPressedTime = System.currentTimeMillis();
		} else {
			super.onBackPressed();
		}
	}

	private boolean inRootFragment() {
		return getSupportFragmentManager().getBackStackEntryCount() == 0;
	}

	public void onEventMainThread(HttpErrorEvent httpErrorEvent) {
		EventBus.getDefault().registerSticky(httpErrorEvent);
		Utils.showMsgDialog(this, httpErrorEvent.errMsg);
	}
}
