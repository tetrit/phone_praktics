package ru.mirea.AleevAV.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentDateTime = findViewById(R.id.textView);
        setInitialDateTime();
    }

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

        public void OnClickShowDialog(View view) {
            AlertDialogFragment alertDialog = new AlertDialogFragment();
            alertDialog.show(getSupportFragmentManager(), "mirea");
        }

        public void onOkClicked() {
            Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"возможно\"!",
                    Toast.LENGTH_LONG).show();
        }

        public void onCancelClicked() {
            Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"нет\"!",
                    Toast.LENGTH_LONG).show();
        }

        public void onNeutralClicked() {
            Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"вроде нет\"!",
                    Toast.LENGTH_LONG).show();
        }

        public void OnClickTimeDialog(View view) {

            new TimePickerDialog(MainActivity.this, t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();
        }

        private void setInitialDateTime() {

            currentDateTime.setText(DateUtils.formatDateTime(this,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_SHOW_TIME));
        }

        public void OnClickDate(View view)
        {
            new DatePickerDialog(MainActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        }

        public void OnClickProgress(View view)
        {

            ProgressBarClass progress = new ProgressBarClass(this);
            progress.show();

            new Handler().postDelayed(()-> progress.dismiss(), 3000);
        }
}