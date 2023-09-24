package com.example.uniliver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import java.text.DecimalFormat;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class data_display extends AppCompatActivity {
    private TextView textViewUnilever;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        textViewUnilever = findViewById(R.id.textViewUnilever);
        DBHelper dbHelper = new DBHelper(this);
        HashMap<String, Float> brandPercentageMap = dbHelper.calculateBrandUsagePercentage();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        TextView textViewUnilever = findViewById(R.id.textViewUnilever);
        textViewUnilever.setText("Unilever: " + decimalFormat.format(brandPercentageMap.get("Unilever")) + "%");

        TextView textViewJandJ = findViewById(R.id.textViewJandJ);
        textViewJandJ.setText("Johnson & Johnson: " + decimalFormat.format(brandPercentageMap.get("Johnson & Johnson")) + "%");

        TextView textViewPandG = findViewById(R.id.textViewPandG);
        textViewPandG.setText("P & G: " + decimalFormat.format(brandPercentageMap.get("P & G")) + "%");

        TextView textViewDanone = findViewById(R.id.textViewDanone);
        textViewDanone.setText("Danone: " + decimalFormat.format(brandPercentageMap.get("Danone")) + "%");

        TextView textViewNestle = findViewById(R.id.textViewNestle);
        textViewNestle.setText("Nestle: " + decimalFormat.format(brandPercentageMap.get("Nestle")) + "%");
    }
}