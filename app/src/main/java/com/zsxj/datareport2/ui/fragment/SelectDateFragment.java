package com.zsxj.datareport2.ui.fragment;

import com.squareup.timessquare.CalendarPickerView;
import com.zsxj.datareport2.R;
import com.zsxj.datareport2.event.SelectDateEvent;
import com.zsxj.datareport2.model.HttpResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

/**
 * Created by sen on 15-5-14.
 * Last Modified by
 */
@EFragment(R.layout.fragment_select_date)
public class SelectDateFragment extends BaseFragment {

    @FragmentArg
    SelectDateEvent mEvent;

    @FragmentArg
    Date mMin;

    @FragmentArg
    Date mMax;

    @ViewById(R.id.calendar_view)
    CalendarPickerView mCalendarView;

    @AfterViews
    void init() {
        mCalendarView.init(mMin, mMax).withSelectedDate(mMin);
    }

    @Click(R.id.done_button)
    void done() {
//        LocalDate selected = new LocalDate(mCalendarView.getSelectedDate());
//        if (mEvent instanceof SelectStartDateEvent) {
//            EventBus.getDefault().postSticky(new ReturnStartDateEvent(selected.toString(Utils.DATE_PATTERN)));
//        } else {
//            EventBus.getDefault().postSticky(new ReturnEndDateEvent(selected.toString(Utils.DATE_PATTERN)));
//        }
    }

    public void onEvent(HttpResult event) {};
}
