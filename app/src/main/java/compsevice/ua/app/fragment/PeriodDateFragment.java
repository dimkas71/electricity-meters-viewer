package compsevice.ua.app.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import compsevice.ua.app.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PeriodDateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PeriodDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodDateFragment extends Fragment implements DatePickerFragment.OnDateSetListener, View.OnClickListener {


    private static final SimpleDateFormat FORMATTER;

    static {
        FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    }


    public static final String DATE_FROM_KEY = "DATE_FROM_KEY";
    public static final String DATE_TO_KEY = "DATE_TO_KEY";

    private Date fromDate;
    private Date toDate;

    private OnFragmentInteractionListener listener;
    private TextView textDateFrom;
    private TextView textDateTo;
    private Button dateFromButton;
    private Button dateToButton;

    public PeriodDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param from Parameter 1.
     * @param to Parameter 2.
     * @return A new instance of fragment PeriodDateFragment.
     */
    @SuppressLint("SimpleDateFormat")
    public static PeriodDateFragment newInstance(Date from, Date to) {
        PeriodDateFragment fragment = new PeriodDateFragment();
        Bundle args = new Bundle();
        args.putString(DATE_FROM_KEY, FORMATTER.format(from));
        args.putString(DATE_TO_KEY, FORMATTER.format(to));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                fromDate = FORMATTER.parse(getArguments().getString(DATE_FROM_KEY));
                toDate = FORMATTER.parse(getArguments().getString(DATE_TO_KEY));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            fromDate = new Date();
            toDate = new Date();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_period_date, container, false);

        textDateFrom = (TextView)view.findViewById(R.id.text_date_from);

        textDateFrom.setText(Html.fromHtml(getString(R.string.text_date_from, FORMATTER.format(fromDate))));

        textDateTo = (TextView)view.findViewById(R.id.text_date_to);

        textDateTo.setText(Html.fromHtml(getString(R.string.text_date_to, FORMATTER.format(toDate))));


        DatePickerFragment datePicker = (DatePickerFragment) getFragmentManager().findFragmentByTag("datePicker");

        if (datePicker == null) {
            datePicker = new DatePickerFragment();
            getFragmentManager().beginTransaction().add(datePicker, "datePicker");

        }

        dateFromButton = (Button)view.findViewById(R.id.button_from);
        dateFromButton.setOnClickListener(this);


        dateToButton = (Button)view.findViewById(R.id.button_to);
        dateToButton.setOnClickListener(this);

        datePicker.setListener((DatePickerFragment.OnDateSetListener) this);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        DatePickerFragment datePicker = (DatePickerFragment) getFragmentManager().findFragmentByTag("datePicker");
        switch (id) {
            case R.id.button_from:
                datePicker.show(getFragmentManager(), "datePicker");
                break;
            case R.id.button_to:
                datePicker.show(getFragmentManager(), "datePicker");
                break;
            default:

        }

    }

    @Override
    public void onDateSet(int year, int month, int dayOfMonth) {
        Log.i(PeriodDateFragment.class.getSimpleName(), "Year: " + year);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
