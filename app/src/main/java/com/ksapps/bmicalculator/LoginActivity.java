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

import static android.content.Context.MODE_PRIVATE;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginName;
    Button btnLogin;
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etLoginName.getText().toString();
                if(name.length()== 0){
                    etLoginName.setError("Please enter Name");
                    etLoginName.requestFocus();
                    return;
                }

                String[] names = dbH.getNames();
                int i=0;
                while(i<names.length){
                    if(name.equals(names[i++])){
                        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("name",name);
                        editor.commit();

                        Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i1);
                        finish();
                        return;
                    }
                }
                if(i== names.length) {
                    Toast.makeText(LoginActivity.this, "No User with name " + name,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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
