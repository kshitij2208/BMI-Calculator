package com.ksapps.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.R.attr.id;
import static android.R.attr.name;

public class DatabaseHandler extends SQLiteOpenHelper{

    SQLiteDatabase db;
    Context context;

    public DatabaseHandler(Context context) {
        super(context, "BMIHistoryDB", null, 1);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE history(date TEXT,bmi FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    public void addItem(String date, double bmi){
        ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("bmi",bmi);
        long rid = db.insert("history",null,values);
        Log.d("f",""+rid);
        if(rid<0){
            Toast.makeText(context, "Data Not Saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Data Saved in History", Toast.LENGTH_SHORT).show();
        }
    }

    public String getAllItems(){
        Cursor cursor = db.query("history",new String[]{"date","bmi"},null,null,null,null,null);
        int dateColumn = cursor.getColumnIndex("date");
        int bmiColumn = cursor.getColumnIndex("bmi");
        String allItems="";
        cursor.moveToFirst();

        if((cursor!= null && (cursor.getCount() > 0))||allItems.length() == 0){
            do{
                String date = cursor.getString(dateColumn);
                String bmi = cursor.getString(bmiColumn);
                allItems = allItems + date+"\nBMI: "+bmi+"\n\n";
            }while (cursor.moveToNext());
        }else{
            Toast.makeText(context, "No Results to Show", Toast.LENGTH_SHORT).show();
        }
        return allItems;
    }


}
