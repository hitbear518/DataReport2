package com.zsxj.datareport2.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.model.HttpResult;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

/**
 * Created by Sen on 2/23/2015.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

//	@ViewById(R.id.progress)
//	View mProgressView;
//
//	@ViewById(R.id.content_root)
//	View mContentRootView;

	@ViewById(R.id.progress_on_top)
	View mProgressOnTop;

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

	protected void showProgress(boolean show) {
		mProgressOnTop.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}

	public void onEvent(HttpResult result) {
		EventBus.getDefault().removeStickyEvent(result);
	}
}
