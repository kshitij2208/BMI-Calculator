package com.ksapps.bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DatabaseHandler dbH = new DatabaseHandler(this);

        tvHistory = (TextView) findViewById(R.id.tvHistory);

        String items = dbH.getAllItems();
        tvHistory.setText(items);
    }
}
