package com.zsxj.datareport2.ui.fragment;

import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

/**
 * Created by Sen on 2/23/2015.
 */
public abstract class BaseFragment extends Fragment {

	@Override
	public void onResume() {
		super.onResume();
		EventBus.getDefault().registerSticky(this, 1);
	}

	@Override
	public void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);
	}
}
