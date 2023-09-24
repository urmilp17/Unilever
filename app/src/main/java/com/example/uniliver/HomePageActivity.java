package com.example.uniliver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class HomePageActivity extends AppCompatActivity {

    Button b;
    ImageButton bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        b = findViewById(R.id.add_survey);
        bb = findViewById(R.id.imageButton);

        b.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewSurvey.class);
            startActivity(intent);
        });

        bb.setOnClickListener(v -> {
            Intent intent = new Intent(this, data_display.class);
            startActivity(intent);
        });
    }
}