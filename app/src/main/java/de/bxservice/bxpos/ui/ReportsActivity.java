package de.bxservice.bxpos.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import de.bxservice.bxpos.R;
import de.bxservice.bxpos.logic.DataProvider;
import de.bxservice.bxpos.ui.dialog.DatePickerFragment;

public class ReportsActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    private Button  fromButton, toButton;
    private boolean isFromDate;
    //The dates will are stored as int in yyyymmdd format
    private long fromDate, toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromButton = (Button) findViewById(R.id.from_button);
        toButton = (Button) findViewById(R.id.to_button);

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1; //Calendar month returns the position of the month 0 being January
        int day = c.get(Calendar.DAY_OF_MONTH);

        setFromButtonText(year, month, day);
        setToButtonText(year, month, day);
        fromDate = getFormattedDate(year, month, day, 0, 0); //From the beginning of the day
        toDate   = getFormattedDate(year, month, day,23,59); //To the end of the day

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.query_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("From: " + fromDate + "To: " + toDate);
                System.out.println(new DataProvider(getBaseContext()).getPaidOrders(fromDate, toDate).size());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * On Button listener defined in the xml file
     * @param view
     */
    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.from_button:
                isFromDate = true;
                showDatePickerDialog();
                break;

            case R.id.to_button:
                isFromDate = false;
                showDatePickerDialog();
                break;
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        if (isFromDate) {
            setFromButtonText(year, month+1, day);
            fromDate = getFormattedDate(year, month+1, day, 0, 0); //From the beginning of the day
        }
        else {
            setToButtonText(year, month+1, day);
            toDate = getFormattedDate(year, month+1, day, 23, 59); //To the end of the day
        }

    }

    private void setFromButtonText(int year, int month, int day) {
        StringBuilder date = new StringBuilder();
        if(day < 10)
            date.append("0");
        date.append(day).append(".");
        if(month < 10)
            date.append("0");
        date.append(month).append(".").append(year);

        fromButton.setText(getString(R.string.from_date, date));
    }

    private void setToButtonText(int year, int month, int day) {
        StringBuilder date = new StringBuilder();
        if(day < 10)
            date.append("0");
        date.append(day).append(".");
        if(month < 10)
            date.append("0");
        date.append(month).append(".").append(year);
        toButton.setText(getString(R.string.to_date, date));
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     * @return formatted date
     */
    private long getFormattedDate(int year, int month, int day, int hour, int minute) {
        StringBuilder date = new StringBuilder();

        date.append(year);

        if(month < 10)
            date.append("0");
        date.append(month);

        if(day < 10)
            date.append("0");
        date.append(day);

        if(hour < 10)
            date.append("0");
        date.append(hour);

        if(minute < 10)
            date.append("0");
        date.append(minute);

        return Long.parseLong(date.toString());
    }


}