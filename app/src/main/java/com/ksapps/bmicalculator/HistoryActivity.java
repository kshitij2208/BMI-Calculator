package com.ksapps.bmicalculator;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    TextView tvHistory;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        String name = sp1.getString("currentName","");

        DatabaseHandler dbH = new DatabaseHandler(this);

        tvHistory = (TextView) findViewById(R.id.tvHistory);

        String items = dbH.getAllItems(name);
        tvHistory.setText(items);
    }
}
