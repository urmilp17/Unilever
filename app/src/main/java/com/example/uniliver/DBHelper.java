package com.example.uniliver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "survey";
    public static final int DATABASE_VERSION = 1;

    public static final String F_TABLE_NAME = "codes";
    public static final String COLUMN_CODE = "code";

    private static final String TABLE_NAME = "SurveyData";

    // Define column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USE_UNILEVER = "use_unilever";
    private static final String COLUMN_BRAND_SELECTED = "brand_selected";
    private static final String COLUMN_PURCHASE_FREQUENCY = "purchase_frequency";
    private static final String COLUMN_RECOMMENDATION = "recommendation";
    private static final String COLUMN_FEEDBACK = "feedback";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_USE_UNILEVER + " INTEGER, " +
                    COLUMN_BRAND_SELECTED + " TEXT, " +
                    COLUMN_PURCHASE_FREQUENCY + " TEXT, " +
                    COLUMN_RECOMMENDATION + " TEXT, " +
                    COLUMN_FEEDBACK + " TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String tquery = "create table " + TABLE_NAME + "(code  INTEGER)";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCode(int code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE,code);
        long result = db.insert(TABLE_NAME,null,cv);
        return result == -1 ? false : true;
    }

    public long insertFormData(String name, String email, int useUnilever, String brandSelected, String purchaseFrequency, String recommendation, String feedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = -1;

        try {
            // Check if checkBoxUseUnilever is checked, then set brandSelected as "Unilever"
            if (useUnilever == 1) {
                brandSelected = "Unilever";
            }

            if (feedback.isEmpty()) {
                feedback = "N/A";
            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_USE_UNILEVER, useUnilever);
            values.put(COLUMN_BRAND_SELECTED, brandSelected);
            values.put(COLUMN_PURCHASE_FREQUENCY, purchaseFrequency);
            values.put(COLUMN_RECOMMENDATION, recommendation);
            values.put(COLUMN_FEEDBACK, feedback);

            newRowId = db.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return newRowId;
    }

    public HashMap<String, Float> calculateBrandUsagePercentage() {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, Float> brandPercentageMap = new HashMap<>();

        String[] columns = {COLUMN_BRAND_SELECTED};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            int brandSelectedColumnIndex = cursor.getColumnIndex(COLUMN_BRAND_SELECTED);

            if (brandSelectedColumnIndex != -1) {
                int totalUsers = cursor.getCount();
                if (totalUsers > 0) {
                    // Initialize counts for each brand
                    int unileverCount = 0;
                    int jandjCount = 0;
                    int pandgCount = 0;
                    int danoneCount = 0;
                    int nestleCount = 0;

                    while (cursor.moveToNext()) {
                        String brandSelected = cursor.getString(brandSelectedColumnIndex);
                        switch (brandSelected) {
                            case "Unilever":
                                unileverCount++;
                                break;
                            case "J and J":
                                jandjCount++;
                                break;
                            case "PandG":
                                pandgCount++;
                                break;
                            case "Danone":
                                danoneCount++;
                                break;
                            case "Nestle":
                                nestleCount++;
                                break;
                        }
                    }

                    // Calculate percentages
                    brandPercentageMap.put("Unilever", (float) unileverCount / totalUsers * 100);
                    brandPercentageMap.put("Johnson & Johnson", (float) jandjCount / totalUsers * 100);
                    brandPercentageMap.put("P & G", (float) pandgCount / totalUsers * 100);
                    brandPercentageMap.put("Danone", (float) danoneCount / totalUsers * 100);
                    brandPercentageMap.put("Nestle", (float) nestleCount / totalUsers * 100);
                }
            }

            cursor.close();
        }

        db.close();
        return brandPercentageMap;
    }



}

