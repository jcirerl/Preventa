package tpv.cirer.com.marivent.herramientas;

/**
 * Created by JUAN on 11/07/2015.
 */

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;
    private long minDate;
    private long maxDate;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");

        minDate = args.getLong("minDate");
        maxDate = args.getLong("maxDate");
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
//        pickerDialog.getDatePicker().setMaxDate(maxDate);
        pickerDialog.getDatePicker().setMinDate(minDate);
        pickerDialog.getDatePicker().setMaxDate(maxDate);
        return pickerDialog;
//        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
}