package com.ksapps.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult,tvUnderweight,tvNormal,tvOverweight,tvObese;
    Button btnBack,btnShare,btnSave;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        final DatabaseHandler dbH = new DatabaseHandler(this);

        tvResult = (TextView)findViewById(R.id.tvResult);
        tvUnderweight = (TextView)findViewById(R.id.tvUnderweight);
        tvNormal = (TextView)findViewById(R.id.tvNormal);
        tvOverweight = (TextView)findViewById(R.id.tvOverweight);
        tvObese = (TextView)findViewById(R.id.tvObese);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnShare = (Button)findViewById(R.id.btnShare);
        btnSave = (Button)findViewById(R.id.btnSave);

        btnSave.setEnabled(true);

        final Intent i = getIntent();
        double bmi = Math.round(i.getDoubleExtra("bmi",0)*100);
        bmi = bmi/100;
        String bmiType="";

        if(bmi<18.5){
            bmiType="Underweight";
            tvUnderweight.setTextColor(Color.parseColor("#ff0000"));
        } else if(bmi>=18.5&&bmi<25){
            bmiType="Normal";
            tvNormal.setTextColor(Color.parseColor("#ff0000"));
        } else if(bmi>=25&&bmi<30){
            bmiType="Overweight";
            tvOverweight.setTextColor(Color.parseColor("#ff0000"));
        } else{
            bmiType="Obese";
            tvObese.setTextColor(Color.parseColor("#ff0000"));
        }
        tvResult.setText("Your BMI is "+bmi+" and you are "+bmiType);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        String name = sp1.getString("currentName","");
        String age = sp1.getString("currentAge","");
        String phone = sp1.getString("currentPhone","");
        String gender = sp1.getString("currentGender","");
        final String msg = "Name:"+name+"\nAge: "+age+"\nPhone: "+phone+"\nGender: "+gender+"\nBMI: "
                +bmi+"\nYou are "+bmiType;

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);
            }
        });
        final double finalBmi = bmi;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
                String name = sp1.getString("currentName","");

                String currentTime = Calendar.getInstance().getTime().toString();
                dbH.addItem(name,currentTime, finalBmi);

                btnSave.setEnabled(false);
            }
        });
    }
}
