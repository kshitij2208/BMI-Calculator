package com.ksapps.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static android.R.attr.name;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    TextView tvName;
    Spinner spFeet,spInch;
    EditText etWeight;
    Button btnCalculate,btnViewHistory;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        tvName = (TextView)findViewById(R.id.tvWelcome);
        spFeet = (Spinner)findViewById(R.id.spFeet);
        spInch = (Spinner)findViewById(R.id.spInch);
        etWeight = (EditText)findViewById(R.id.etWeight);
        btnCalculate = (Button)findViewById(R.id.btnCalculate);
        btnViewHistory = (Button)findViewById(R.id.btnViewHistory);

        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        String name = sp1.getString("currentName","");
        tvName.setText("Welcome : "+ name);

        final Integer[] feets ={1,2,3,4,5,6,7,8,9};
        final Integer[] inches = {0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayAdapter<Integer> feetAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1,
                feets);
        spFeet.setAdapter(feetAdapter);
        ArrayAdapter<Integer> inchAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1,
                inches);
        spInch.setAdapter(inchAdapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weigh = etWeight.getText().toString();
                int feet = feets[spFeet.getSelectedItemPosition()]*12;
                int inch = inches[spInch.getSelectedItemPosition()];
                double height = (feet+inch)*0.0254;
                double weight;
                if(weigh.length() != 0)
                    weight = Integer.parseInt(weigh);
                else{
                    weight = 0;
                }
                double bmi = weight/(height*height);

                if(weight == 0 || weight > 450){
                    etWeight.setError("Please enter your proper weight");
                    etWeight.requestFocus();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("bmi",bmi);
                startActivity(intent);
            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.LogOut){
            int flag = 1;
            sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putInt("flag",flag);
            editor.commit();

            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        }
        if(item.getItemId() == R.id.About){
            Snackbar.make(findViewById(android.R.id.content),"App developed by KSapps",Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
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
