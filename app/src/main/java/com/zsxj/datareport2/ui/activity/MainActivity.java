package com.zsxj.datareport2.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.ui.adapter.DrawerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

/**
 * Created by sen on 15-5-8.
 * Last Modified by
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseMainActivity {

	@ViewById(R.id.toolbar)
	Toolbar mToolbar;
	@ViewById(R.id.drawer_layout)
	DrawerLayout mDrawerLayout;
	@ViewById(R.id.drawer_list)
	ListView mDrawerList;

	@Bean
	DrawerAdapter mDrawerAdapter;

	@AfterViews
	void init() {
		setSupportActionBar(mToolbar);

		// Set up Drawer
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_menu, R.string.close_drawer_menu);
		mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSupportFragmentManager().popBackStackImmediate();
			}
		});
		mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Init drawer list
		mDrawerList.setAdapter(mDrawerAdapter);

		// Set up OnBackStackChangeListener
		FragmentManager fm = getSupportFragmentManager();
		fm.addOnBackStackChangedListener(this);
		// Select first item
		selectDrawerItem(0);
	}

	private void selectDrawerItem(int position) {
		mDrawerList.setItemChecked(position, true);
		drawerItemClicked(position);
	}

	@ItemClick(R.id.drawer_list)
	public void drawerItemClicked(int position) {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		Fragment fragment = (Fragment) mDrawerList.getItemAtPosition(position);
		fm.beginTransaction().replace(R.id.content, fragment).commit();
		mDrawerLayout.closeDrawers();
	}

	@Click(R.id.settings_button)
	void settingsClicked() {

	}
}
