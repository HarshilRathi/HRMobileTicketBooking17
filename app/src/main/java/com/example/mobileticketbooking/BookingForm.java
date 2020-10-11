package com.example.mobileticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class BookingForm extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour;
    TextView Mname,email;
    EditText NoOfTickets,Date;
    RadioButton Mtime;
    RadioGroup radioGroup;
    Button BookForm,datebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        Mname=(TextView)findViewById(R.id.Mname);
        email=(TextView)findViewById(R.id.uemail);
        NoOfTickets=(EditText)findViewById(R.id.no_of_tickets);
        Date=(EditText)findViewById(R.id.in_date);
        radioGroup = (RadioGroup) findViewById(R.id.MovieTime);
        BookForm = (Button) findViewById(R.id.BookForm);
        datebtn=(Button)findViewById(R.id.btn_date);
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        Intent i=getIntent();
        String moviename=getIntent().getExtras().getString("moviename");
        String uemail=getIntent().getExtras().getString("email");
        Mname.setText(moviename);
        email.setText(uemail);
        BookForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Mtime = (RadioButton) findViewById(selectedId);
                int noofticket=Integer.parseInt(NoOfTickets.getText().toString());

                if (selectedId == -1) {
                    Toast.makeText(BookingForm.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookingForm.this, Mtime.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(BookingForm.this,BookingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}