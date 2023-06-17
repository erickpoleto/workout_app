package com.app.workout_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateStarredDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "exercises.db";
    private static final int VER = 1;

    public static final String tableName = "starred";
    public static final String colNameExerciseId = "exercise_id";
    public static final String colNameUserId = "user_id";

    public CreateStarredDatabase(Context context){
        super(context, DB_NAME,null,VER);
    }
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tableName + " (" +
                    colNameExerciseId + " INTEGER," +
                    colNameUserId + " INTEGER" +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
