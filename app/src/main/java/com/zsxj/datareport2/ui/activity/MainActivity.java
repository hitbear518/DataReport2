package com.zsxj.datareport2.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.HttpErrorEvent;
import com.zsxj.datareport2.event.ReturnDateEvent;
import com.zsxj.datareport2.event.SelectDateEvent;
import com.zsxj.datareport2.network.RequestHelper;
import com.zsxj.datareport2.ui.adapter.DrawerAdapter;
import com.zsxj.datareport2.ui.fragment.SelectDateFragment_;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

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

    private EventHelper mEventHelper = new EventHelper();
    private RequestHelper mRequestHelper;

    @AfterViews
    void init() {
        mRequestHelper = new RequestHelper(this);

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
        DrawerAdapter drawerAdapter = new DrawerAdapter(this);
        mDrawerList.setAdapter(drawerAdapter);

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

    @Override
    protected void registerEventListener() {
        EventBus.getDefault().registerSticky(mEventHelper);
    }

    @Override
    protected void unregisterEventListener() {
        EventBus.getDefault().unregister(mEventHelper);
    }

    private class EventHelper {
        public void onEventMainThread(HttpErrorEvent httpErrorEvent) {
            Utils.showMsgDialog(MainActivity.this, httpErrorEvent.errMsg);
        }

        public void onEventMainThread(SelectDateEvent event) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content,
                    SelectDateFragment_.builder()
                            .mMin(event.min)
                            .mMax(event.max)
                            .mEvent(event)
                            .build())
                    .addToBackStack(null).commit();
        }

        public void onEventMainThread(ReturnDateEvent event) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
