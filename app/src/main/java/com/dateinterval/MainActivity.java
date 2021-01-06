package com.dateinterval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText inputDate;
    private EditText inputDateFim;
    private TextView qtdDias;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDate = findViewById(R.id.editDate);
        inputDateFim = findViewById(R.id.editDateFim);
        qtdDias = findViewById(R.id.qtdDias);
        inputDate.setInputType(InputType.TYPE_NULL);
        inputDateFim.setInputType(InputType.TYPE_NULL);
        CheckBox checkToday = findViewById(R.id.checkbox_today);

        if (checkToday.isChecked()) {
            myCalendar = Calendar.getInstance();
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String today = date.format(myCalendar.getTime());
            inputDateFim.setText(today);
            inputDateFim.setEnabled(false);
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener datefim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFim();
            }
        };

        inputDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        inputDateFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                new DatePickerDialog(MainActivity.this, datefim, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        inputDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelFim() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        inputDateFim.setText(sdf.format(myCalendar.getTime()));
    }

    public void calcular(View view){
        String firstDate = String.valueOf(inputDate.getText());
        String secondDate = String.valueOf(inputDateFim.getText());
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            Date dtini = sdf.parse(firstDate);
            Date dtfim = sdf.parse(secondDate);
            assert dtfim != null;
            assert dtini != null;
            long dif = dtfim.getTime() - dtini.getTime();
            long difference = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
            qtdDias.setText(difference + " dias.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClickCheckbox (View v) {
        boolean checked = ((CheckBox) v).isChecked();
        if (checked) {
            myCalendar = Calendar.getInstance();
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String today = date.format(myCalendar.getTime());
            inputDateFim.setText(today);
            inputDateFim.setEnabled(false);
        }
        else {
            inputDateFim.setText("");
            inputDateFim.setEnabled(true);
        }
    }
}