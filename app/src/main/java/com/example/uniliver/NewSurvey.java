package com.example.uniliver;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewSurvey extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextFeedback;
    RadioGroup radioGroupBrands, radioGroupPurchaseFrequency, radioGroupRecommendation;
    CheckBox checkBoxUseUnilever;
    Button submitButton;

    public String Brand;

    private Button buttonBrand1;
    private Button buttonBrand2;
    private Button buttonBrand3;
    private Button buttonBrand4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);

        radioGroupBrands = findViewById(R.id.radioGroupBrands);
        buttonBrand1 = findViewById(R.id.JandJ);
        buttonBrand2 = findViewById(R.id.PandG);
        buttonBrand3 = findViewById(R.id.Danone);
        buttonBrand4 = findViewById(R.id.Nestle);


        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFeedback = findViewById(R.id.editTextFeedback);

        radioGroupBrands = findViewById(R.id.radioGroupBrands);
        radioGroupPurchaseFrequency = findViewById(R.id.radioGroupPurchaseFrequency);
        radioGroupRecommendation = findViewById(R.id.radioGroupRecommendation);

        // Initialize CheckBox
        checkBoxUseUnilever = findViewById(R.id.checkBoxUseUnilever);

        // Initialize Submit Button
        submitButton = findViewById(R.id.sbutton);

        submitButton.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String feedback = editTextFeedback.getText().toString();

            int useUnilever = checkBoxUseUnilever.isChecked() ? 1 : 0;
            String brandSelected = getSelectedRadioButtonText(radioGroupBrands);
            String purchaseFrequency = getSelectedRadioButtonText(radioGroupPurchaseFrequency);
            String recommendation = getSelectedRadioButtonText(radioGroupRecommendation);

            try {
            DBHelper db = new DBHelper(getApplicationContext());
            db.insertFormData(name, email, useUnilever, brandSelected, purchaseFrequency, recommendation, feedback);
            SQLiteDatabase dbss = db.getWritableDatabase();
                Toast.makeText(this, "Record Inserted", Toast.LENGTH_SHORT).show();
            } catch(Exception e){
                Toast.makeText(this, "Error: "+ e, Toast.LENGTH_SHORT).show();
            }

        });
    }






    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton radioButton = findViewById(selectedRadioButtonId);
            return radioButton.getText().toString();
        }
        return "";
    }
}
