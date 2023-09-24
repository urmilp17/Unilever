package com.example.uniliver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {


    private EditText pinEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinEditText = findViewById(R.id.pinEditText);

//        try {
//            DBHelper dbs = new DBHelper(this);
//            SQLiteDatabase dbss = dbs.getWritableDatabase();
//            Toast.makeText(this, "Table Created", Toast.LENGTH_SHORT).show();
//        } catch(Exception e){
//            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
//        }

    }

    public void checkPIN(View view) {
        int pin = Integer.parseInt(pinEditText.getText().toString());

        if (isCodeExists(pin)) {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect PIN. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCodeExists(int code) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    DBHelper.F_TABLE_NAME,
                    null,
                    DBHelper.COLUMN_CODE + " = ?",
                    new String[]{String.valueOf(code)},
                    null,
                    null,
                    null
            );
            return cursor.getCount() > 0;
        } catch (SQLException e) {
            Toast.makeText(this, "Error "+ e, Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

}
