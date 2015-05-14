package com.zsxj.datareport2.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsxj.datareport2.R;
import com.zsxj.datareport2.ui.fragment.SalesFragment_;

/**
 * Created by Wang Sen on 3/8/2015.
 * Last modified:
 * By:
 */
public class DrawerAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles = {
            "销售统计"
    };

    public DrawerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        switch (position) {
        case 0:
            return new SalesFragment_();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, parent, false);
        }

        TextView drawerText = (TextView) convertView;
        drawerText.setText(mTitles[position]);

        return convertView;
    }
}
