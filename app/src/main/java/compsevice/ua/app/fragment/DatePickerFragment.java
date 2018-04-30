package compsevice.ua.app.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public void setListener(OnDateSetListener listener) {
        this.listener = listener;
    }

    private OnDateSetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();

        return new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i(DatePickerFragment.class.getSimpleName(), "You choose day of month:" + dayOfMonth);
        if (listener != null) {
            listener.onDateSet(year, month, dayOfMonth);
        }
    }

    public interface OnDateSetListener {
        void onDateSet(int year, int month, int dayOfMonth);
    }

}