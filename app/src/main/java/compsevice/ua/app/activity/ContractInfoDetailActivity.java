package compsevice.ua.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import compsevice.ua.app.R;
import compsevice.ua.app.fragment.DatePickerFragment;
import compsevice.ua.app.fragment.PeriodDateFragment;

public class ContractInfoDetailActivity extends AppCompatActivity implements PeriodDateFragment.OnFragmentInteractionListener {

    private Button datePickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_info_detail);

        datePickerButton = (Button)findViewById(R.id.button_date_picker);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();

                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(this.getClass().getSimpleName(), "Uri: " + uri.toString());
    }
}
