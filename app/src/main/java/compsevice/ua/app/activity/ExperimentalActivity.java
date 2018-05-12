package compsevice.ua.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import compsevice.ua.app.R;
import compsevice.ua.app.fragment.DatePickerFragment;

public class ExperimentalActivity extends AppCompatActivity {

    private static final String TAG = ExperimentalActivity.class.getSimpleName();

    private boolean collapsed = false;

    private Date from;
    private Date to;

    private TextView headerTextView;
    private TextView upDownTextView;
    private TextView fromTextView;
    private TextView toTextView;
    private ImageView fromImageView;
    private ImageView toImageView;

    public void onClick(View view) {
        int id = view.getId();
        Log.i(TAG, "Cliced on a view  with id " + id);
        DatePickerFragment dp = null;
        switch (id) {
            case R.id.text_view_up_down:

                //if collapsed then arrow should be down otherwise the arrow should be up...
                //when collapsed textView should not be visible anymore and otherwise it should be visible...
                collapsed = !collapsed;

                setBackground();
                updateVisibility();
                break;
            case R.id.image_view_from:
                dp = (DatePickerFragment)getSupportFragmentManager().findFragmentByTag("datePicker");
                if (dp == null) {
                    dp = new DatePickerFragment();
                }
                dp.setListener(new DatePickerFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int month, int dayOfMonth) {
                        Log.i(TAG, "From " + String.format("%d.%d.%d", dayOfMonth, month, year));
                        Calendar instance = Calendar.getInstance();
                        instance.set(year, month, dayOfMonth);
                        from = instance.getTime();
                        updatePeriodContent();
                    }
                });
                dp.show(getSupportFragmentManager(), "datePicker");

                break;
            case R.id.image_view_to:
                dp = (DatePickerFragment)getSupportFragmentManager().findFragmentByTag("datePicker");
                if (dp == null) {
                    dp = new DatePickerFragment();
                }
                dp.setListener(new DatePickerFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int month, int dayOfMonth) {
                        Log.i(TAG, "To " + String.format("%d.%d.%d", dayOfMonth, month, year));
                        Calendar instance = Calendar.getInstance();
                        instance.set(year, month, dayOfMonth);
                        to = instance.getTime();
                        updatePeriodContent();
                    }
                });
                dp.show(getSupportFragmentManager(), "datePicker");
            default:
                break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental);

        headerTextView = (TextView)findViewById(R.id.text_view_header);
        fromTextView = (TextView) findViewById(R.id.text_view_from);
        fromImageView = (ImageView) findViewById(R.id.image_view_from);
        toTextView = (TextView) findViewById(R.id.text_view_to);
        toImageView = (ImageView) findViewById(R.id.image_view_to);
        upDownTextView = (TextView) findViewById(R.id.text_view_up_down);
        onClick(upDownTextView);


        Calendar now = Calendar.getInstance();

        Calendar fromCal = Calendar.getInstance();
        fromCal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.getMinimum(Calendar.DAY_OF_MONTH));

        from = fromCal.getTime();

        Calendar toCal = Calendar.getInstance();

        toCal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.getMaximum(Calendar.DAY_OF_MONTH));

        to = toCal.getTime();

        updatePeriodContent();


    }

    private void updateVisibility() {
        if (collapsed) {
            fromTextView.setVisibility(View.GONE);
            fromImageView.setVisibility(View.GONE);
            toTextView.setVisibility(View.GONE);
            toImageView.setVisibility(View.GONE);
        } else {
            fromTextView.setVisibility(View.VISIBLE);
            fromImageView.setVisibility(View.VISIBLE);
            toTextView.setVisibility(View.VISIBLE);
            toImageView.setVisibility(View.VISIBLE);
        }
    }

    private void setBackground() {

        if (collapsed) {
            upDownTextView.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_green_24dp, getTheme()));
        } else {
            //expanded here...
            upDownTextView.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_green_24dp, getTheme()));
        }
    }

    private void updatePeriodContent() {

        final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String periodByString = getResources().getString(R.string.text_date_period, formatter.format(from), formatter.format(to));
        headerTextView.setText(periodByString);

        fromTextView.setText(Html.fromHtml(getResources().getString(R.string.text_date_from, formatter.format(from))));
        toTextView.setText(Html.fromHtml(getResources().getString(R.string.text_date_to, formatter.format(to))));
    }
}
