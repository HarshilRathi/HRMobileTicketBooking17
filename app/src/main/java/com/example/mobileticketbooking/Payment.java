package com.example.mobileticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Payment extends AppCompatActivity {
Button pay;
RadioGroup radioGroup;
RadioButton payMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        radioGroup = (RadioGroup) findViewById(R.id.Moviepayment);
        pay = (Button) findViewById(R.id.pay);


       pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                payMode = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(Payment.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Payment.this, payMode.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Payment.this,DonePayment.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}