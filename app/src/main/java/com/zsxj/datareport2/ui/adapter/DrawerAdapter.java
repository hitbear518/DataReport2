package com.zsxj.datareport2.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.ui.activity.MainActivity;
import com.zsxj.datareport2.ui.fragment.DaySalesFragment2_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringArrayRes;

/**
 * Created by Wang Sen on 3/8/2015.
 * Last modified:
 * By:
 */
@EBean
public class DrawerAdapter extends BaseAdapter {

    @RootContext
    MainActivity mMainActivity;

    @StringArrayRes(R.array.drawer_items)
    String[] mTitles;

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        switch (position) {
        case 0:
            return DaySalesFragment2_.builder().build();
        default:
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mMainActivity).inflate(R.layout.drawer_list_item, parent, false);
        }

        TextView drawerText = (TextView) convertView;
        drawerText.setText(mTitles[position]);

        return convertView;
    }
}
