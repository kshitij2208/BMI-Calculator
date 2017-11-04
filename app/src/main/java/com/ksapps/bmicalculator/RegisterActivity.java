package com.ksapps.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        String name = sp1.getString("name", "");

        if (sp1.getInt("flag",0) == 0) {
            if (name.equals("")) {
                setBtnRegister();
            } else {

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
            }
        });
    }

    public void setBtnRegister(){

        final DatabaseHandler dbH = new DatabaseHandler(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String phone = etPhone.getText().toString();
                RadioButton rbGender = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
                String gender = rbGender.getText().toString();
                String error = "INVALID:\n";
                if (name.length() == 0) {
                    error += "Name\n";
                }
                if (age.length() == 0) {
                    error += "Age\n";
                }
                if (phone.length() != 10) {
                    error += "Phone";
                }
                if (error.length() != 9) {
                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] names = dbH.getNames();
                int i = 0;
                while(i<names.length){
                    if(name.equals(names[i++])){
                        etName.setError("User already exists");
                        etName.requestFocus();
                        return;
                    }
                }

                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("name", name);
                editor.putString("age", age);
                editor.putString("phone", phone);
                editor.putString("gender", gender);
                editor.putInt("flag",flag);
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