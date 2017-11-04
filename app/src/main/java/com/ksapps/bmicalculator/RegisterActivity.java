package com.ksapps.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.R.attr.name;
import static android.R.id.edit;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etAge, etPhone;
    RadioGroup rgGender;
    Button btnRegister;
    RadioButton rbMale, rbFemale;
    TextView tvAlready;
    SharedPreferences sp1;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        tvAlready = (TextView)findViewById(R.id.tvAlready);

        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        String name = sp1.getString("currentName", "/");
        Log.d("names",name+" "+sp1.getInt("flag",0));
        if (sp1.getInt("flag",0) == 0) {
            if (name.equals("/")) {
                setBtnRegister();
            } else {
                Log.d("hello","hello");
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }else{
            setBtnRegister();
        }

        tvAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void setBtnRegister(){

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);

                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String phone = etPhone.getText().toString();
                RadioButton rbGender = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
                String gender = rbGender.getText().toString();
                String error = "INVALID:\n";

                if (name.length() == 0) {
                    error += "Name\n";
                }
                if (age.length() == 0 || Integer.parseInt(age) > 120) {
                    error += "Age\n";
                }
                if (phone.length() != 10) {
                    error += "Phone";
                }
                if (error.length() != 9) {
                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> names = Arrays.asList(sp1.getString("name","").split("\\s*,\\s*"));
                int i = 0;
                Log.d("hello",sp1.getString("name",""));

                while(i<names.size()){
                    if(name.equals(names.get(i++))){
                        etName.setError("User already exists");
                        etName.requestFocus();
                        return;
                    }
                }

                String spName = sp1.getString("name", "");
                String spAge = sp1.getString("age","");
                String spPhone = sp1.getString("phone","");
                String spGender = sp1.getString("gender","");

                if(spName.length() != 0) {
                    spName = spName + "," + name;
                    spAge = spAge + "," + age;
                    spPhone = spPhone + "," + phone;
                    spGender = spGender + "," + gender;
                } else {
                    spName = name;
                    spAge = age;
                    spPhone = phone;
                    spGender = gender;
                }

                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("currentName", name);
                editor.putString("currentAge",age);
                editor.putString("currentPhone",phone);
                //Log.d("current",age+ " " + phone);
                editor.putString("currentGender",gender);
                editor.putString("name", spName);
                Log.d("h1",sp1.getString("name",""));
                editor.putString("age", spAge);
                editor.putString("phone", spPhone);
                editor.putString("gender", spGender);
                editor.putInt("flag",0);
                Log.d("names"," "+sp1.getInt("flag",0));
                editor.commit();

                Intent i1 = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i1);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }
}