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
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ksapps.bmicalculator.R.id.etName;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginName;
    Button btnLogin,btnBack2;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        final DatabaseHandler dbH = new DatabaseHandler(this);

        etLoginName = (EditText)findViewById(R.id.etLoginName);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnBack2 = (Button)findViewById(R.id.btnBack2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);

                String name = etLoginName.getText().toString();
                if(name.length()== 0){
                    etLoginName.setError("Please enter Name");
                    etLoginName.requestFocus();
                    return;
                }

                List<String> names = Arrays.asList(sp1.getString("name","").split("\\s*,\\s*"));
                List<String> ages = Arrays.asList(sp1.getString("age","").split("\\s*,\\s*"));
                List<String> phones = Arrays.asList(sp1.getString("phone","").split("\\s*,\\s*"));
                List<String> genders = Arrays.asList(sp1.getString("gender","").split("\\s*,\\s*"));
                int i = 0;
                while(i<names.size()){
                    if(name.equals(names.get(i))){

                        //sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("currentName", name);
                        editor.putString("currentAge",ages.get(i));
                        editor.putString("currentPhone", phones.get(i));
                        editor.putString("currentGender",genders.get(i));
                        editor.putInt("flag",0);
                        editor.commit();

                        Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i1);
                        finish();
                        break;
                    }
                    i++;
                }
                if(i== names.size()) {
                    Toast.makeText(LoginActivity.this, "No User with name " + name,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent i1 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i1);
        finish();
    }
}
