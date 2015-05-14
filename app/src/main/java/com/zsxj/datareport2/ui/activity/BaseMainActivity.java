package com.zsxj.datareport2.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.ui.fragment.ProgressFragment;

/**
 * Created by sen on 15-5-9.
 * Last Modified by
 */
public abstract class BaseMainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

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
        mDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0 || isLoading());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerEventListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterEventListener();
    }

    @Override
    public void onBackPressed() {
        // Can't cancel request for now
        if (isLoading()) {
            return;
        }

        // Implement "Press back again to exit"
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0 && mBackPressedTime + TIME_INTERVAL <= System.currentTimeMillis()) {
            Toast.makeText(getApplicationContext(), R.string.prompt_press_back_again_to_exit, Toast.LENGTH_SHORT).show();
            mBackPressedTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract void registerEventListener();

    protected abstract void unregisterEventListener();

    protected boolean isLoading() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        return fragment instanceof ProgressFragment;
    }
}
