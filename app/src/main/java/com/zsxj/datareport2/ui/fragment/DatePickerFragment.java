package com.zsxj.datareport2.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.zsxj.datareport2.event.ReturnDateEvent;
import com.zsxj.datareport2.utils.Utils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.joda.time.LocalDate;

import de.greenrobot.event.EventBus;

/**
 * Created by sen on 15-5-18.
 */
@EFragment
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	@FragmentArg
	LocalDate mInitialDate;

	@FragmentArg
	ReturnDateEvent mEvent;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), this, mInitialDate.getYear(), mInitialDate.getMonthOfYear() - 1, mInitialDate.getDayOfMonth());
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		LocalDate localDate = new LocalDate(year, monthOfYear + 1, dayOfMonth);
		mEvent.date = localDate.toString(Utils.DATE_PATTERN);
		EventBus.getDefault().post(mEvent);
	}
}
